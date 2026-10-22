package net.threeisolutions.approvals.api.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.DeliveryMode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryConfigDto {

    @NotNull
    private DeliveryMode mode;

    @Size(max = 249)
    private String topic;

    @Size(max = 2000)
    private String url;

    @Size(max = 200)
    private String secretRef;

    @JsonIgnore
    @AssertTrue(message = "WEBHOOK needs url and secretRef")
    public boolean isComplete() {
        return mode != DeliveryMode.WEBHOOK || (url != null && secretRef != null);
    }
}
