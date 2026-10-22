package net.threeisolutions.approvals.api.dto.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.ChainMode;
import net.threeisolutions.approvals.api.enums.OnEmptyAction;
import net.threeisolutions.approvals.api.enums.OnRejectAction;

/// How the stages produced by the rules form a chain, and what happens when there are none
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChainDto {

    @NotNull
    @Builder.Default
    private ChainMode mode = ChainMode.SEQUENTIAL;

    /// Default for rules that don't set their own.
    @NotNull
    @Builder.Default
    private OnRejectAction onReject = OnRejectAction.ABORT_CHAIN;

    /// FAIL by default: an empty chain is never a silent approval.
    @NotNull
    @Builder.Default
    private OnEmptyAction onEmpty = OnEmptyAction.FAIL;

    @Valid
    private ApproverSelectorDto fallbackApprover;

    @JsonIgnore
    @AssertTrue(message = "fallbackApprover is required when onEmpty is FALLBACK")
    public boolean isFallbackConsistent() {
        return onEmpty != OnEmptyAction.FALLBACK || fallbackApprover != null;
    }
}
