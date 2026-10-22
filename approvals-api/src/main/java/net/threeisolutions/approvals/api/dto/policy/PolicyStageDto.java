package net.threeisolutions.approvals.api.dto.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.dto.PrincipalRefDto;
import net.threeisolutions.approvals.api.enums.EvidenceRequirement;
import net.threeisolutions.approvals.api.enums.OnExpiryAction;
import net.threeisolutions.approvals.api.enums.OnRejectAction;
import net.threeisolutions.approvals.api.enums.StageMode;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyStageDto {

    @NotBlank
    @Size(max = 200)
    private String name;

    @NotNull
    private StageMode mode;
    private Integer quorumSize;

    @NotEmpty
    @Builder.Default
    private List<@Valid @NotNull PrincipalRefDto> approvers = new ArrayList<>();

    @NotNull
    @Builder.Default
    private EvidenceRequirement evidenceRequirement = EvidenceRequirement.NONE;

    @NotNull
    @Builder.Default
    private OnRejectAction onReject = OnRejectAction.ABORT_CHAIN;
    private Duration sla;

    @NotNull
    @Builder.Default
    private OnExpiryAction onExpiry = OnExpiryAction.EXPIRE;

    @JsonIgnore
    @AssertTrue(message = "quorumSize is required for QUORUM, between 1 and the number of approvers, "
            + "and not allowed otherwise")
    public boolean isQuorumConsistent() {
        if (mode != StageMode.QUORUM) {
            return quorumSize == null;
        }
        return quorumSize != null && quorumSize >= 1 && (approvers == null || quorumSize <= approvers.size());
    }
}
