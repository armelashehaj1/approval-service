package net.threeisolutions.approvals.service.dal.repository;

import net.threeisolutions.approvals.service.dal.entity.TenantCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantCalendarRepository extends JpaRepository<TenantCalendar, UUID> {

    Optional<TenantCalendar> findByTenantId(String tenantId);
}
