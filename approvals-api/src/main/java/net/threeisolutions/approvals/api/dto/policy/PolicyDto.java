package net.threeisolutions.approvals.api.dto.policy;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDto {

    public static final int CURRENT_SCHEMA_VERSION = 1;

    @Min(1)
    @Builder.Default
    private int schemaVersion = CURRENT_SCHEMA_VERSION;

    @NotEmpty
    @Builder.Default
    private List<@Valid @NotNull PolicyStageDto> stages = new ArrayList<>();
}
