package net.threeisolutions.approvals.api.dto.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.DedupeMode;
import net.threeisolutions.approvals.api.enums.PipelineStepType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PipelineStepDto {

    public static final String REQUESTER = "requester";

    @NotNull
    private PipelineStepType type;

    @Size(max = 200)
    private String principal;

    private DedupeMode dedupe;

    @JsonIgnore
    @AssertTrue(message = "EXCLUDE_PRINCIPAL needs principal and DEDUPE needs dedupe")
    public boolean isComplete() {
        if (type == PipelineStepType.EXCLUDE_PRINCIPAL) {
            return principal != null;
        }
        return type != PipelineStepType.DEDUPE || dedupe != null;
    }
}
