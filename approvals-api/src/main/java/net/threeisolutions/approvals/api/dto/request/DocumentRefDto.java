package net.threeisolutions.approvals.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.DocumentRole;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRefDto {

    @NotNull
    private DocumentRole role;

    @NotNull
    private UUID fileId;

    @NotBlank
    @Size(max = 255)
    private String filename;

    @NotBlank
    @Size(max = 100)
    private String contentType;
    @Positive
    private Long sizeBytes;

    @Pattern(regexp = "^[0-9a-f]{64}$")
    private String sha256;
}
