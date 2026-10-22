package net.threeisolutions.approvals.api.dto.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.PickMode;
import net.threeisolutions.approvals.api.enums.SelectorType;

/// Finds approvers in the client's directory. Which fields apply depends on type
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApproverSelectorDto {

    @NotNull
    private SelectorType type;

    @Size(max = 200)
    private String principalId;

    @Size(max = 200)
    private String role;

    @Size(max = 100)
    private String relation;

    @Min(1)
    private Integer hops;

    @Size(max = 100)
    private String attribute;

    @NotNull
    @Builder.Default
    private PickMode pick = PickMode.ALL;

    @JsonIgnore
    @AssertTrue(message = "PRINCIPAL needs principalId, ROLE needs role, GRAPH_WALK needs relation and hops, "
            + "ATTRIBUTE_OWNER needs attribute")
    public boolean isComplete() {
        if (type == null) {
            return true;
        }
        return switch (type) {
            case PRINCIPAL -> principalId != null;
            case ROLE -> role != null;
            case GRAPH_WALK -> relation != null && hops != null;
            case ATTRIBUTE_OWNER -> attribute != null;
        };
    }
}
