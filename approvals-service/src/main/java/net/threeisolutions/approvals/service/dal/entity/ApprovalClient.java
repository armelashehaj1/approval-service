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
import net.threeisolutions.approvals.api.dto.client.ClientConfigDto;
import net.threeisolutions.approvals.api.enums.ClientAuthMode;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "approval_client",
        uniqueConstraints = @UniqueConstraint(name = "uq_client_code", columnNames = "code"))
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalClient extends BaseEntity {

    /// Stable public name of the client. what the client authenticates as
    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_mode", nullable = false)
    private ClientAuthMode authMode;

    /// Rulesets this client may reference. Null means none
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "policy_scope")
    private List<String> policyScope;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "config", nullable = false)
    private ClientConfigDto config;

    @Column(name = "enabled", nullable = false)
    @Builder.Default
    private boolean enabled = true;
}
