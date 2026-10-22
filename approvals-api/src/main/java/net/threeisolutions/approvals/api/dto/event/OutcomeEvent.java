package net.threeisolutions.approvals.api.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.dto.response.DecisionResponse;
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
public class OutcomeEvent {

    private UUID eventId;
    private UUID requestId;
    private String clientCode;
    private String externalRef;

    @Builder.Default
    private Map<String, Object> correlation = new HashMap<>();

    private RequestStatus status;
    private TerminalSource terminalSource;
    private Instant resolvedAt;

    @Builder.Default
    private List<DecisionResponse> decisions = new ArrayList<>();
}
