package net.threeisolutions.approvals.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.ClientAuthMode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhoAmIResponse {
    private String clientCode;
    private String tenantId;
    private ClientAuthMode authenticatedBy;
}
