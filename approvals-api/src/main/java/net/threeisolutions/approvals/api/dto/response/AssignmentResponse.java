package net.threeisolutions.approvals.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.AssignmentState;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponse {

    private UUID id;
    private String principalId;

    /** Who the duty was meant for when it was delegated; null otherwise. */
    private String originalPrincipalId;

    private String displayName;
    private AssignmentState state;
    private Instant assignedAt;

    /** Null until decided. */
    private DecisionResponse decision;
}
