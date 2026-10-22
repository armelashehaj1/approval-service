package net.threeisolutions.approvals.api.dto.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.dto.client.ClientConfigDto;

/// Replaces the display name and the whole config
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientRequest {

    @NotBlank
    @Size(max = 200)
    private String displayName;

    @NotNull
    @Valid
    private ClientConfigDto config;
}
