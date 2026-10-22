package net.threeisolutions.approvals.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.EvidenceRequirement;
import net.threeisolutions.approvals.api.enums.StageMode;
import net.threeisolutions.approvals.api.enums.StageStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StageResponse {

    private UUID id;
    private int seq;
    private String name;
    private StageMode mode;
    private Integer quorumSize;
    private EvidenceRequirement evidenceRequirement;
    private StageStatus status;
    private Instant activatedAt;
    private Instant slaAt;
    private Instant expiresAt;
    private Instant resolvedAt;

    @Builder.Default
    private List<AssignmentResponse> assignments = new ArrayList<>();
}
