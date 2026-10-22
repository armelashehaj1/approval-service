package net.threeisolutions.approvals.service.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.dto.client.ClientConfigDto;
import net.threeisolutions.approvals.api.enums.ClientAuthMode;
import net.threeisolutions.approvals.service.dal.entity.ApprovalClient;

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

    public static ClientContext of(ApprovalClient client) {
        return ClientContext.builder()
                .clientId(client.getId())
                .code(client.getCode())
                .tenantId(client.getTenantId())
                .authenticatedBy(client.getAuthMode())
                .config(client.getConfig())
                .build();
    }

    public boolean mayUse(String ruleset) {
        return config != null && config.mayUse(ruleset);
    }

    public boolean inlinePolicyAllowed() {
        return config != null && config.isAllowInlinePolicy();
    }
}
