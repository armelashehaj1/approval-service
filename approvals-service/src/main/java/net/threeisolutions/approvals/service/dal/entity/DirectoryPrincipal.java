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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "directory_principal",
        uniqueConstraints = @UniqueConstraint(name = "uq_directory_principal",
                columnNames = {"client_id", "principal_id"}))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryPrincipal extends BaseEntity {

    @Column(name = "client_id", nullable = false, updatable = false)
    private UUID clientId;

    @Column(name = "principal_id", nullable = false, updatable = false)
    private String principalId;

    @Column(name = "email")
    private String email;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true;

    /// May point at a principal that does not exist, resolution records that as unresolved
    @Column(name = "manager_id")
    private String managerId;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "roles", nullable = false)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    /// Highest amount this principal may approve as a delegate. Null means no limit
    @Column(name = "authority_limit", precision = 19, scale = 4)
    private BigDecimal authorityLimit;

    @Column(name = "teams_user_id")
    private String teamsUserId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attributes", nullable = false)
    @Builder.Default
    private Map<String, Object> attributes = new HashMap<>();

    @Column(name = "synced_at", nullable = false)
    @Builder.Default
    private Instant syncedAt = Instant.now();
}
