package net.threeisolutions.approvals.api.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.ChannelType;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelSettingsResponse {
    private String tenantId;
    private ChannelType channel;
    private boolean enabled;
    private Map<String, Object> settings;
    private String secretRef;
}
