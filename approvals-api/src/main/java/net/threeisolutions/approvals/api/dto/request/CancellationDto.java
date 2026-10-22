package net.threeisolutions.approvals.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.EvidencePurgePolicy;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancellationDto {

    /// If the requester may cancel
    @Builder.Default
    private boolean requesterMayCancel = true;

    // Null means the tenant default
    private EvidencePurgePolicy evidencePurge;
}
