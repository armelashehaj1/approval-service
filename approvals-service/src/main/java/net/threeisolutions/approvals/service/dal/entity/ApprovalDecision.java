package net.threeisolutions.approvals.service.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.threeisolutions.approvals.api.enums.DecisionChannel;
import net.threeisolutions.approvals.api.enums.DecisionOutcome;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;


@Entity
@Table(name = "approval_decision",
        uniqueConstraints = @UniqueConstraint(name = "uq_decision_per_assignment", columnNames = "assignment_id"),
        indexes = @Index(name = "ix_decision_request", columnList = "request_id, decided_at"))
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDecision extends BaseEntity {

    @Column(name = "request_id", nullable = false)
    private UUID requestId;

    @Column(name = "stage_id", nullable = false)
    private UUID stageId;

    @Column(name = "assignment_id", nullable = false)
    private UUID assignmentId;

    @Column(name = "actor_principal_id", nullable = false)
    private String actorPrincipalId;

    @Column(name = "on_behalf_of")
    private String onBehalfOf;

    @Enumerated(EnumType.STRING)
    @Column(name = "outcome", nullable = false)
    private DecisionOutcome outcome;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name = "evidence_id")
    private UUID evidenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false)
    private DecisionChannel channel;

    @Column(name = "decided_at", nullable = false)
    @Builder.Default
    private Instant decidedAt = Instant.now();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "auth_context")
    private Map<String, Object> authContext;

    @Column(name = "prev_hash")
    private byte[] prevHash;

    @Column(name = "record_hash", nullable = false)
    private byte[] recordHash;
}
