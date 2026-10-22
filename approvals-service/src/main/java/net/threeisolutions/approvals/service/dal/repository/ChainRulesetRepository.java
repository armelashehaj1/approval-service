package net.threeisolutions.approvals.service.dal.repository;

import net.threeisolutions.approvals.service.dal.entity.ChainRuleset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChainRulesetRepository extends JpaRepository<ChainRuleset, UUID> {

    Optional<ChainRuleset> findByTenantIdAndNameAndVersion(String tenantId, String name, int version);

    Optional<ChainRuleset> findTopByTenantIdAndNameOrderByVersionDesc(String tenantId, String name);

    List<ChainRuleset> findByTenantIdOrderByNameAscVersionDesc(String tenantId);

    List<ChainRuleset> findByTenantIdAndNameOrderByVersionDesc(String tenantId, String name);
}
