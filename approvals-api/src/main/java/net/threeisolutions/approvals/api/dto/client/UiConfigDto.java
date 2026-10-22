package net.threeisolutions.approvals.api.dto.client;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/// Links back into the client's own UI, shown to approvers next to a task
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UiConfigDto {
    @Size(max = 2000)
    private String taskUrlTemplate;
}
