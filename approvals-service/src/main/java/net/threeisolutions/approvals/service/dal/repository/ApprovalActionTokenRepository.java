package net.threeisolutions.approvals.service.dal.repository;

import net.threeisolutions.approvals.service.dal.entity.ApprovalActionToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApprovalActionTokenRepository extends JpaRepository<ApprovalActionToken, UUID> {
}
