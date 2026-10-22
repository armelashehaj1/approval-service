package net.threeisolutions.approvals.api.dto.admin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/// How a tenant reaches approvers on one channel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelSettingsRequest {

    @Builder.Default
    private boolean enabled = true;

    /// Channel-specific, non-secret settings
    @NotNull
    @Builder.Default
    private Map<String, Object> settings = new HashMap<>();

    /// Reference to the credential in the secret store, never the secret itself
    @Size(max = 200)
    private String secretRef;
}
