package net.threeisolutions.approvals.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.dto.PrincipalRefDto;
import net.threeisolutions.approvals.api.enums.RequestStatus;
import net.threeisolutions.approvals.api.enums.TerminalSource;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequestResponse {

    private UUID id;
    private String clientCode;
    private String idempotencyKey;
    private String externalRef;

    @Builder.Default
    private Map<String, Object> correlation = new HashMap<>();

    private PrincipalRefDto requester;
    private RequestStatus status;
    private TerminalSource terminalSource;
    private String rulesetRef;
    private Integer rulesetVersion;

    /// Hex SHA-256 of the policy snapshot the request is authorized against
    private String policyHash;

    private int currentStageSeq;
    private String cancelledBy;
    private String cancelReason;
    private Instant createdDate;
    private Instant slaAt;
    private Instant expiresAt;
    private Instant resolvedAt;

    @Builder.Default
    private List<StageResponse> stages = new ArrayList<>();
}
