package net.threeisolutions.approvals.service.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;
import net.threeisolutions.approvals.service.dto.enums.ActionTokenPurpose;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.net.InetAddress;
import java.time.Instant;
import java.util.UUID;

/// Token behind an emailed /a/{token} link. Only its hash is stored.
@Entity
@Table(name = "approval_action_token", uniqueConstraints = @UniqueConstraint(name = "uq_action_token_hash", columnNames = "token_hash"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalActionToken extends BaseEntity {
    @Column(name = "assignment_id", nullable = false, updatable = false)
    private UUID assignmentId;

    @Column(name = "token_hash", nullable = false, updatable = false)
    private byte[] tokenHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", nullable = false)
    private ActionTokenPurpose purpose;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "used_at")
    private Instant usedAt;

    @JdbcTypeCode(SqlTypes.INET)
    @Column(name = "used_ip")
    private InetAddress usedIp;
}
