package net.threeisolutions.approvals.service.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.threeisolutions.approvals.api.dto.policy.PolicyDto;
import net.threeisolutions.approvals.api.enums.RequestStatus;
import net.threeisolutions.approvals.api.enums.TerminalSource;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Entity
@Table(name = "approval_request",
        uniqueConstraints = @UniqueConstraint(name = "uq_request_idem",
                columnNames = {"tenant_id", "client_id", "idempotency_key"}),
        indexes = {
                @Index(name = "ix_request_tenant_status", columnList = "tenant_id, status"),
                @Index(name = "ix_request_external", columnList = "client_id, external_ref")
        })
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequest extends BaseEntity {

    @Column(name = "tenant_id", nullable = false, updatable = false)
    private String tenantId;

    @Column(name = "client_id", nullable = false, updatable = false)
    private UUID clientId;

    @Column(name = "idempotency_key", nullable = false, updatable = false)
    private String idempotencyKey;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "correlation", nullable = false)
    @Builder.Default
    private Map<String, Object> correlation = new HashMap<>();

    @Column(name = "external_ref")
    private String externalRef;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "requester", nullable = false)
    private Map<String, Object> requester;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "subject", nullable = false)
    @Builder.Default
    private Map<String, Object> subject = new HashMap<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "context")
    private Map<String, Object> context;

    @Column(name = "ruleset_ref")
    private String rulesetRef;

    @Column(name = "ruleset_version")
    private Integer rulesetVersion;

    /// Verbatim snapshot of the chain the request was created with; never rewritten.
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "policy", nullable = false, updatable = false)
    private PolicyDto policy;

    @Column(name = "policy_hash", nullable = false, updatable = false)
    private byte[] policyHash;

    @Column(name = "policy_schema_version", nullable = false, updatable = false)
    private int policySchemaVersion;

    /**
     * Ordered resolution steps, including principals that could not be resolved.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "resolution_trace")
    @Builder.Default
    private List<Map<String, Object>> resolutionTrace = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "cancellation")
    private Map<String, Object> cancellation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "terminal_source")
    private TerminalSource terminalSource;

    @Column(name = "cancelled_by")
    private String cancelledBy;

    @Column(name = "cancel_reason", columnDefinition = "text")
    private String cancelReason;

    @Column(name = "current_stage_seq", nullable = false)
    @Builder.Default
    private int currentStageSeq = 1;

    @Column(name = "sla_at")
    private Instant slaAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;

    @Version
    @Column(name = "version", nullable = false)
    private long version;
}
