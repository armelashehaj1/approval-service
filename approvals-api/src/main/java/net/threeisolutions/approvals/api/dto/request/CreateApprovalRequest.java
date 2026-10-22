package net.threeisolutions.approvals.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.dto.PrincipalRefDto;
import net.threeisolutions.approvals.api.dto.policy.PolicyDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApprovalRequest {
    @NotBlank
    @Size(max = 200)
    private String idempotencyKey;

    @Builder.Default
    private Map<String, Object> correlation = new HashMap<>();

    @Size(max = 200)
    private String externalRef;

    @NotNull
    @Valid
    private PrincipalRefDto requester;

    @Valid
    private SubjectDto subject;

    @Builder.Default
    private Map<String, Object> context = new HashMap<>();

    @Size(max = 200)
    private String rulesetRef;

    @Valid
    private PolicyDto policy;

    @Valid
    private DeadlinesDto deadlines;

    @Builder.Default
    private List<@Valid @NotNull DocumentRefDto> documents = new ArrayList<>();

    @Valid
    private CancellationDto cancellation;

    @JsonIgnore
    @AssertTrue(message = "exactly one of rulesetRef or policy must be set")
    public boolean isPolicySourceUnambiguous() {
        return (rulesetRef == null) != (policy == null);
    }
}
