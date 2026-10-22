package net.threeisolutions.approvals.api.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.dto.client.ClientConfigDto;
import net.threeisolutions.approvals.api.enums.ClientAuthMode;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {
    private UUID id;
    private String code;
    private String tenantId;
    private String displayName;
    private ClientAuthMode authMode;
    private boolean enabled;
    private ClientConfigDto config;
    private Instant createdDate;
}