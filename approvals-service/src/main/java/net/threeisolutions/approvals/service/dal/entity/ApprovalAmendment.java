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
import net.threeisolutions.approvals.api.enums.AmendmentKind;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "approval_amendment",
        uniqueConstraints = @UniqueConstraint(name = "uq_amendment_seq", columnNames = {"request_id", "seq"}))
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalAmendment extends BaseEntity {

    @Column(name = "request_id", nullable = false)
    private UUID requestId;

    @Column(name = "seq", nullable = false)
    private int seq;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false)
    private AmendmentKind kind;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "body", nullable = false)
    private Map<String, Object> body;

    @Column(name = "actor", nullable = false)
    private String actor;

    @Column(name = "reason", nullable = false, columnDefinition = "text")
    private String reason;

    @Column(name = "policy_hash_before", nullable = false)
    private byte[] policyHashBefore;

    @Column(name = "policy_hash_after", nullable = false)
    private byte[] policyHashAfter;
}
