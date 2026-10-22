package net.threeisolutions.approvals.service.config.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Data
@Validated
@ConfigurationProperties("approvals.security")
public class ApprovalsSecurityProperties {
    @NotBlank
    private String apiKeyHeader = "X-Api-Key";
    @Valid
    @NotNull
    private OAuth oauth = new OAuth();

    @Data
    public static class OAuth {
        private String issuerUri;
        private List<@NotBlank String> audiences = new ArrayList<>();
        @NotEmpty
        private List<@NotBlank String> permissionClaims = new ArrayList<>(List.of("scope"));
        @NotBlank
        private String principalClaim = "sub";
        @NotBlank
        private String emailClaim = "email";
        @NotBlank
        private String nameClaim = "name";
        private String mfaClaim;
        @NotBlank
        private String mfaValue = "mfa";

        @AssertTrue(message = "audiences are required when issuer-uri is set")
        boolean isAudienceConfigured() {
            return !StringUtils.hasText(issuerUri) || !audiences.isEmpty();
        }
    }
}
