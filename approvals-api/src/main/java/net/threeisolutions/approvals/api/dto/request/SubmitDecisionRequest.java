package net.threeisolutions.approvals.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.DecisionOutcome;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitDecisionRequest {

    @NotNull
    private DecisionOutcome outcome;

    @Size(max = 4000)
    private String comment;

    /// Required when the stage asks for SIGNED_FILE evidence
    private UUID evidenceId;
    private Map<String, Object> contextPatch;
}
