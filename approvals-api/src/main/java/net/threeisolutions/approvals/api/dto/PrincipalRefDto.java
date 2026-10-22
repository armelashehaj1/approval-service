package net.threeisolutions.approvals.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalRefDto {

    @NotBlank
    @Size(max = 200)
    private String id;

    @Email
    @Size(max = 320)
    private String email;

    @Size(max = 200)
    private String displayName;
}
