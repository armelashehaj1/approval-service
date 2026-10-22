package net.threeisolutions.approvals.service.dal.repository;

import net.threeisolutions.approvals.service.dal.entity.ApprovalClientSecret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApprovalClientSecretRepository extends JpaRepository<ApprovalClientSecret, UUID> {

    Optional<ApprovalClientSecret> findByClientIdAndKeyId(UUID clientId, String keyId);
}
