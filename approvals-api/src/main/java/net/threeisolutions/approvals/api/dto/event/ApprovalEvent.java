package net.threeisolutions.approvals.api.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.ApprovalEventType;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalEvent {

    private UUID eventId;
    private ApprovalEventType type;
    private Instant occurredAt;
    private String tenantId;
    private String clientCode;
    private UUID requestId;

    @Builder.Default
    private Map<String, Object> correlation = new HashMap<>();

    /** Event-specific fields, e.g. stage seq or decision outcome. */
    @Builder.Default
    private Map<String, Object> payload = new HashMap<>();
}
