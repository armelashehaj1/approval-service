package net.threeisolutions.approvals.service.config.security;

import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.api.enums.ClientAuthMode;
import net.threeisolutions.approvals.service.dal.entity.ApprovalClient;
import net.threeisolutions.approvals.service.dal.repository.ApprovalClientRepository;
import net.threeisolutions.approvals.service.util.exception.ClientNotAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientContextResolver {

    private final ApprovalClientRepository clients;

    @Transactional(readOnly = true)
    public ClientContext resolve(Authentication authentication) {
        ApprovalClient client = switch (authentication) {
            case ApiKeyAuthenticationToken apiKey -> clients.findById(apiKey.getPrincipal())
                    .filter(c -> c.getAuthMode() == ClientAuthMode.API_KEY)
                    .orElseThrow(() -> new ClientNotAuthorizedException("Client is not set up for API keys"));
            case JwtAuthenticationToken jwt -> fromJwt(jwt.getToken())
                    .orElseThrow(() -> new ClientNotAuthorizedException("Token does not belong to a known client"));
            default -> throw new ClientNotAuthorizedException("Unsupported authentication");
        };
        if (!client.isEnabled()) {
            throw new ClientNotAuthorizedException("Client is disabled");
        }
        return ClientContext.builder()
                .clientId(client.getId())
                .code(client.getCode())
                .tenantId(client.getTenantId())
                .authenticatedBy(client.getAuthMode())
                .config(client.getConfig())
                .build();
    }

    private Optional<ApprovalClient> fromJwt(Jwt jwt) {
        String clientId = Optional.ofNullable(jwt.getClaimAsString("azp")).orElse(jwt.getClaimAsString("client_id"));
        if (clientId == null) {
            return Optional.empty();
        }
        return clients.findByOAuthClient(clientId)
                .filter(c -> issuerMatches(c, jwt));
    }

    private static boolean issuerMatches(ApprovalClient client, Jwt jwt) {
        String issuer = client.getConfig() == null || client.getConfig().getAuth() == null
                ? null : client.getConfig().getAuth().getIssuer();
        return !StringUtils.hasText(issuer) || jwt.getIssuer() != null && issuer.equals(jwt.getIssuer().toString());
    }
}
