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
import net.threeisolutions.approvals.api.enums.EvidenceRequirement;
import net.threeisolutions.approvals.api.enums.OnRejectAction;
import net.threeisolutions.approvals.api.enums.StageMode;
import net.threeisolutions.approvals.api.enums.StageStatus;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "approval_stage",
        uniqueConstraints = @UniqueConstraint(name = "uq_stage_seq", columnNames = {"request_id", "seq"}))
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalStage extends BaseEntity {

    @Column(name = "request_id", nullable = false, updatable = false)
    private UUID requestId;

    @Column(name = "seq", nullable = false)
    private int seq;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private StageMode mode;

    /// Required when {@link #mode} is QUORUM
    @Column(name = "quorum_size")
    private Integer quorumSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_requirement", nullable = false)
    @Builder.Default
    private EvidenceRequirement evidenceRequirement = EvidenceRequirement.NONE;

    @Enumerated(EnumType.STRING)
    @Column(name = "on_reject", nullable = false)
    @Builder.Default
    private OnRejectAction onReject = OnRejectAction.ABORT_CHAIN;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "on_expiry")
    private Map<String, Object> onExpiry;

    @Column(name = "escalation_round", nullable = false)
    @Builder.Default
    private int escalationRound = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private StageStatus status = StageStatus.WAITING;

    @Column(name = "activated_at")
    private Instant activatedAt;

    @Column(name = "sla_at")
    private Instant slaAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;
}
