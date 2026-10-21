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
import net.threeisolutions.approvals.api.enums.AssignmentState;
import net.threeisolutions.approvals.api.enums.ChannelType;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "approval_assignment",
        uniqueConstraints = @UniqueConstraint(name = "uq_assignment", columnNames = {"stage_id", "principal_id"}),
        indexes = {
                @Index(name = "ix_assignment_inbox", columnList = "principal_id, state"),
                @Index(name = "ix_assignment_request", columnList = "request_id")
        })
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalAssignment extends BaseEntity {

    @Column(name = "request_id", nullable = false, updatable = false)
    private UUID requestId;

    @Column(name = "stage_id", nullable = false, updatable = false)
    private UUID stageId;

    @Column(name = "principal_id", nullable = false)
    private String principalId;

    @Column(name = "original_principal_id")
    private String originalPrincipalId;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "email")
    private String email;

    @Column(name = "teams_user_id")
    private String teamsUserId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "channels", nullable = false)
    @Builder.Default
    private List<ChannelType> channels = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    @Builder.Default
    private AssignmentState state = AssignmentState.PENDING;

    /// The assignment this one replaced when the duty was delegated
    @Column(name = "delegated_from")
    private UUID delegatedFrom;

    @Column(name = "reassigned_at")
    private Instant reassignedAt;

    @Column(name = "reassign_reason", columnDefinition = "text")
    private String reassignReason;

    @Column(name = "assigned_at", nullable = false)
    @Builder.Default
    private Instant assignedAt = Instant.now();

    @Column(name = "notified_at")
    private Instant notifiedAt;

    @Column(name = "last_reminded_at")
    private Instant lastRemindedAt;
}
