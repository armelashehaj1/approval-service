package net.threeisolutions.approvals.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeadlinesDto {
    private Duration sla;
    private Duration expiry;

    @JsonIgnore
    @AssertTrue(message = "sla and expiry must be positive, and sla must not be after expiry")
    public boolean isConsistent() {
        if ((sla != null && (sla.isNegative() || sla.isZero()))
                || (expiry != null && (expiry.isNegative() || expiry.isZero()))) {
            return false;
        }
        return sla == null || expiry == null || sla.compareTo(expiry) <= 0;
    }
}
