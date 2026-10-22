package net.threeisolutions.approvals.service.security;

import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.service.dal.entity.ApprovalClient;
import net.threeisolutions.approvals.service.dal.repository.ApprovalClientRepository;
import net.threeisolutions.approvals.service.util.exception.ClientNotAuthorizedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientContextResolver {

    /// API keys resolve their client in ApiKeyAuthenticationFilter. This handles OAuth tokens only
    @Transactional(readOnly = true)
    public ClientContext resolve(Jwt jwt) {
        ApprovalClient client = fromJwt(jwt)
                .orElseThrow(() -> new ClientNotAuthorizedException("Token does not belong to a known client"));
        if (!client.isEnabled()) {
            throw new ClientNotAuthorizedException("Client is disabled");
        }
        return ClientContext.of(client);
    }

    private final ApprovalClientRepository clients;

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
