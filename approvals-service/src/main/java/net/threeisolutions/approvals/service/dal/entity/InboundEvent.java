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

@Entity
@Table(name = "inbound_event",
        uniqueConstraints = @UniqueConstraint(name = "uq_inbound_message_key", columnNames = "message_key"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundEvent extends BaseEntity {

    /// tenantId:clientCode:idempotencyKey
    @Column(name = "message_key", nullable = false, updatable = false)
    private String messageKey;

    @Column(name = "result", columnDefinition = "text")
    private String result;
}
