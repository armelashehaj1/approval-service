package net.threeisolutions.approvals.service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.api.enums.ClientAuthMode;
import net.threeisolutions.approvals.service.dal.entity.ApprovalClient;
import net.threeisolutions.approvals.service.dal.entity.ApprovalClientSecret;
import net.threeisolutions.approvals.service.dal.repository.ApprovalClientRepository;
import net.threeisolutions.approvals.service.dal.repository.ApprovalClientSecretRepository;
import net.threeisolutions.approvals.service.util.exception.ClientNotAuthorizedException;
import net.threeisolutions.approvals.service.util.helper.ApiKeys;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final String headerName;
    private final ApprovalClientRepository clients;
    private final ApprovalClientSecretRepository secrets;
    private final ProblemResponses problems;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader(headerName);
        if (header == null) {
            chain.doFilter(request, response);
            return;
        }
        Optional<ApprovalClient> client = ApiKeys.parse(header).flatMap(this::authenticate);
        if (client.isEmpty()) {
            SecurityContextHolder.clearContext();
            problems.write(response, HttpStatus.UNAUTHORIZED, "UNAUTHENTICATED", "Invalid API key");
            return;
        }
        if (client.get().getAuthMode() != ClientAuthMode.API_KEY || !client.get().isEnabled()) {
            ClientNotAuthorizedException e = new ClientNotAuthorizedException("Client may not use API keys");
            problems.write(response, e.getStatus(), e.getCode(), e.getMessage());
            return;
        }
        // An API key stands for a calling system, so it carries the client scopes
        SecurityContextHolder.getContext().setAuthentication(new ClientAuthentication(
                ClientContext.of(client.get()),
                AuthorityUtils.createAuthorityList(Scopes.REQUESTS_WRITE, Scopes.REQUESTS_READ)));
        chain.doFilter(request, response);
    }

    private Optional<ApprovalClient> authenticate(ApiKeys.Parsed key) {
        Instant now = Instant.now();
        return clients.findByCode(key.getClientCode())
                .filter(client -> secrets.findByClientIdAndKeyId(client.getId(), key.getKeyId())
                        .filter(secret -> usable(secret, now))
                        .filter(secret -> ApiKeys.matches(key.getSecret(), secret.getSecretHash()))
                        .isPresent());
    }

    private static boolean usable(ApprovalClientSecret secret, Instant now) {
        return secret.getRevokedAt() == null && (secret.getExpiresAt() == null || secret.getExpiresAt().isAfter(now));
    }
}
