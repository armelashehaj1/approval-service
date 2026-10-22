package net.threeisolutions.approvals.service.dal.repository;

import net.threeisolutions.approvals.service.dal.entity.ApprovalClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ApprovalClientRepository extends JpaRepository<ApprovalClient, UUID> {
    Optional<ApprovalClient> findByCode(String code);

    @Query(value = "select * from approval_client "
            + "where auth_mode = 'OAUTH_CC' "
            + "and coalesce(config->'auth'->>'clientId', code) = :clientId",
            nativeQuery = true)
    Optional<ApprovalClient> findByOAuthClient(@Param("clientId") String clientId);
}
