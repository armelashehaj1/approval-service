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
@Table(name = "directory_attribute_owner",
        uniqueConstraints = @UniqueConstraint(name = "uq_directory_attribute_owner",
                columnNames = {"client_id", "attribute", "key"}))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryAttributeOwner extends BaseEntity {

    @Column(name = "client_id", nullable = false, updatable = false)
    private UUID clientId;

    @Column(name = "attribute", nullable = false, updatable = false)
    private String attribute;

    @Column(name = "key", nullable = false, updatable = false)
    private String key;

    @Column(name = "principal_id", nullable = false)
    private String principalId;

    @Column(name = "synced_at", nullable = false)
    @Builder.Default
    private Instant syncedAt = Instant.now();
}
