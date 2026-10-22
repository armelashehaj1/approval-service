package net.threeisolutions.approvals.api.dto.policy;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.ResolutionMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RulesetDto {

    @NotBlank
    @Size(max = 200)
    private String name;

    @Min(1)
    private int version;

    @Builder.Default
    private Map<String, String> contextSchema = new HashMap<>();

    @Builder.Default
    private Map<String, @Valid ContextWritableDto> contextWritable = new HashMap<>();

    @NotEmpty
    @Builder.Default
    private List<@Valid @NotNull RuleDto> rules = new ArrayList<>();

    @Builder.Default
    private List<@Valid @NotNull PipelineStepDto> pipeline = new ArrayList<>();

    @NotNull
    @Valid
    @Builder.Default
    private ChainDto chain = ChainDto.builder().build();

    @NotNull
    @Builder.Default
    private ResolutionMode resolution = ResolutionMode.EAGER;
}
