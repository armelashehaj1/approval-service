package net.threeisolutions.approvals.service.dal.repository;

import net.threeisolutions.approvals.api.enums.ChannelType;
import net.threeisolutions.approvals.service.dal.entity.TenantChannelConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TenantChannelConfigurationRepository extends JpaRepository<TenantChannelConfiguration, UUID> {

    Optional<TenantChannelConfiguration> findByTenantIdAndChannel(String tenantId, ChannelType channel);

    List<TenantChannelConfiguration> findByTenantIdOrderByChannel(String tenantId);
}
