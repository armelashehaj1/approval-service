package net.threeisolutions.approvals.service.config.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.dto.client.ClientConfigDto;
import net.threeisolutions.approvals.api.enums.ClientAuthMode;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientContext {
    private UUID clientId;
    private String code;
    private String tenantId;
    private ClientAuthMode authenticatedBy;
    private ClientConfigDto config;

    public boolean mayUse(String ruleset) {
        return config != null && config.mayUse(ruleset);
    }

    public boolean inlinePolicyAllowed() {
        return config != null && config.isAllowInlinePolicy();
    }
}
