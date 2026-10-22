package net.threeisolutions.approvals.api.dto.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/// Business calendar used for SLA and expiry deadlines
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantCalendarRequest {

    /// IANA zone id, e.g. Europe/Tirane
    @NotBlank
    private String timezone;

    /// ISO day numbers, 1 = Monday
    @NotEmpty
    @Builder.Default
    private List<@NotNull @Min(1) @Max(7) Integer> workDays = new ArrayList<>(List.of(1, 2, 3, 4, 5));

    @Builder.Default
    private List<@NotNull LocalDate> holidays = new ArrayList<>();

    @JsonIgnore
    @AssertTrue(message = "timezone must be an IANA zone id")
    public boolean isTimezoneValid() {
        return timezone == null || ZoneId.getAvailableZoneIds().contains(timezone);
    }
}
