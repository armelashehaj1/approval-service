package net.threeisolutions.approvals.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.DecisionChannel;
import net.threeisolutions.approvals.api.enums.DecisionOutcome;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecisionResponse {
    private UUID id;
    private String actorPrincipalId;
    /// Set when a delegate decided for the original assignee
    private String onBehalfOf;
    private DecisionOutcome outcome;
    private String comment;
    private DecisionChannel channel;
    private Instant decidedAt;
}
