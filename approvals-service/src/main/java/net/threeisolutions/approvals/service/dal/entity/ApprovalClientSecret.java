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

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "approval_client_secret",
        uniqueConstraints = @UniqueConstraint(name = "uq_client_secret_key", columnNames = {"client_id", "key_id"}))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalClientSecret extends BaseEntity {

    @Column(name = "client_id", nullable = false, updatable = false)
    private UUID clientId;

    @Column(name = "key_id", nullable = false, updatable = false)
    private String keyId;

    @Column(name = "secret_hash", nullable = false)
    private byte[] secretHash;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;
}
