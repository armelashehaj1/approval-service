package net.threeisolutions.approvals.api.dto.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.EvidenceRequirement;
import net.threeisolutions.approvals.api.enums.OnExpiryAction;
import net.threeisolutions.approvals.api.enums.OnRejectAction;
import net.threeisolutions.approvals.api.enums.StageMode;

import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDto {

    @Min(1)
    private int seq;

    @NotBlank
    @Size(max = 200)
    private String name;

    /// CEL over `ctx`, e.g. `ctx.total >= 25000.0 && ctx.costCenter == 'IT'`. Null or blank means always.
    @Size(max = 4000)
    private String when;

    @NotNull
    @Valid
    private ApproverSelectorDto approver;

    @NotNull
    @Builder.Default
    private StageMode mode = StageMode.ANY;

    private Integer quorumSize;

    @NotNull
    @Builder.Default
    private EvidenceRequirement evidenceRequirement = EvidenceRequirement.NONE;

    /// Null means the chain default.
    private OnRejectAction onReject;

    private Duration sla;

    @NotNull
    @Builder.Default
    private OnExpiryAction onExpiry = OnExpiryAction.EXPIRE;

    @JsonIgnore
    @AssertTrue(message = "quorumSize is required for QUORUM, at least 1, and not allowed otherwise")
    public boolean isQuorumConsistent() {
        return mode == StageMode.QUORUM ? quorumSize != null && quorumSize >= 1 : quorumSize == null;
    }
}
