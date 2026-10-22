package net.threeisolutions.approvals.api.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantCalendarResponse {
    private String tenantId;
    private String timezone;
    private List<Integer> workDays;
    private List<LocalDate> holidays;
}
