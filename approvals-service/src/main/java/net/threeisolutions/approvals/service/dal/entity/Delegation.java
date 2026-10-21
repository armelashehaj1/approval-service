package net.threeisolutions.approvals.service.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "delegation")
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delegation extends BaseEntity {

    @Column(name = "tenant_id", nullable = false, updatable = false)
    private String tenantId;

    @Column(name = "delegator", nullable = false)
    private String delegator;

    @Column(name = "delegate", nullable = false)
    private String delegate;

    @Column(name = "starts_on", nullable = false)
    private LocalDate startsOn;

    @Column(name = "ends_on", nullable = false)
    private LocalDate endsOn;

    ///CEL expression over the request context; the delegation covers a request only when it is true
    @Column(name = "cap_expr", columnDefinition = "text")
    private String capExpr;

    /// Rulesets the delegation applies to. Null means all.
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "scope")
    private List<String> scope;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true;

    @Column(name = "reason", columnDefinition = "text")
    private String reason;
}
