package net.threeisolutions.approvals.api.dto.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.dto.policy.RulesetDto;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RulesetVersionResponse {
    private UUID id;
    private String tenantId;
    private String name;
    private int version;
    private boolean published;
    private String createdBy;
    private Instant createdDate;
    /// Left out of lists
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RulesetDto body;
}
