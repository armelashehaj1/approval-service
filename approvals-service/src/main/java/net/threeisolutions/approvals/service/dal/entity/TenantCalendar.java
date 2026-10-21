package net.threeisolutions.approvals.service.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tenant_calendar",
        uniqueConstraints = @UniqueConstraint(name = "uq_tenant_calendar", columnNames = "tenant_id"))
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantCalendar extends BaseEntity {

    @Column(name = "tenant_id", nullable = false, updatable = false)
    private String tenantId;

    /// IANA zone id
    @Column(name = "timezone", nullable = false)
    private String timezone;

    /// ISO day numbers, 1 = Monday
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "work_days", nullable = false)
    @Builder.Default
    private List<Integer> workDays = new ArrayList<>(List.of(1, 2, 3, 4, 5));

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "holidays", nullable = false)
    @Builder.Default
    private List<LocalDate> holidays = new ArrayList<>();
}
