package net.threeisolutions.approvals.api.dto.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.dto.client.ClientConfigDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequest {
    /// Stable public name, its part of every API key and the default OAuth client id. Cannot change later
    @NotBlank
    @Size(max = 64)
    @Pattern(regexp = "[a-z0-9][a-z0-9_-]*", message = "lowercase letters, digits, '-' and '_' only")
    private String code;

    @NotBlank
    @Size(max = 100)
    private String tenantId;

    @NotBlank
    @Size(max = 200)
    private String displayName;

    @NotNull
    @Valid
    private ClientConfigDto config;
}
