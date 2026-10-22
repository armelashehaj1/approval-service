package net.threeisolutions.approvals.service.security;

import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.service.config.properties.ApprovalsSecurityProperties;
import net.threeisolutions.approvals.service.util.exception.UserNotAuthorizedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserContextResolver {

    private static final List<String> AUTH_CONTEXT_CLAIMS = List.of("amr", "acrs", "acr", "jti", "uti", "iat", "azp");

    private final ApprovalsSecurityProperties properties;

    public UserContext resolve(Jwt jwt) {
        ApprovalsSecurityProperties.OAuth oauth = properties.getOauth();
        String subject = jwt.getClaimAsString(oauth.getPrincipalClaim());
        if (!StringUtils.hasText(subject)) {
            throw new UserNotAuthorizedException("Token does not identify a user");
        }
        // Entra marks app-only tokens; other IdPs rely on approvals.tasks being granted to users only
        if ("app".equals(jwt.getClaimAsString("idtyp"))) {
            throw new UserNotAuthorizedException("Application tokens cannot act as a user");
        }
        return UserContext.builder()
                .subject(subject)
                .issuer(jwt.getIssuer() == null ? null : jwt.getIssuer().toString())
                .email(jwt.getClaimAsString(oauth.getEmailClaim()))
                .name(jwt.getClaimAsString(oauth.getNameClaim()))
                .mfa(hasMfa(jwt, oauth))
                .authContext(authContext(jwt))
                .build();
    }

    private static boolean hasMfa(Jwt jwt, ApprovalsSecurityProperties.OAuth oauth) {
        if (!StringUtils.hasText(oauth.getMfaClaim())) {
            return false;
        }
        return switch (jwt.getClaims().get(oauth.getMfaClaim())) {
            case String s -> Arrays.asList(s.split(" ")).contains(oauth.getMfaValue());
            case Collection<?> c -> c.stream().map(String::valueOf).anyMatch(oauth.getMfaValue()::equals);
            case null, default -> false;
        };
    }

    private static Map<String, Object> authContext(Jwt jwt) {
        Map<String, Object> context = new LinkedHashMap<>();
        AUTH_CONTEXT_CLAIMS.forEach(claim -> {
            Object value = jwt.getClaims().get(claim);
            if (value != null) {
                context.put(claim, value);
            }
        });
        return context;
    }
}
