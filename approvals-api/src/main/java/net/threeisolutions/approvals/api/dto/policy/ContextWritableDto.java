package net.threeisolutions.approvals.api.dto.policy;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/// A context key approvers may change while deciding, and the stages allowed to change it.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContextWritableDto {
    @NotNull
    private String type;

    @NotEmpty
    @Builder.Default
    private List<String> stages = new ArrayList<>();
}
