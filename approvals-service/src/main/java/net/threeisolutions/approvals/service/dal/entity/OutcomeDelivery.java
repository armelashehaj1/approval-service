package net.threeisolutions.approvals.service.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.threeisolutions.approvals.api.enums.DeliveryMode;
import net.threeisolutions.approvals.api.enums.DeliveryState;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outcome_delivery")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutcomeDelivery extends BaseEntity {

    @Column(name = "request_id", nullable = false, updatable = false)
    private UUID requestId;

    @Column(name = "client_id", nullable = false, updatable = false)
    private UUID clientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private DeliveryMode mode;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    @Builder.Default
    private DeliveryState state = DeliveryState.PENDING;

    @Column(name = "attempts", nullable = false)
    @Builder.Default
    private int attempts = 0;

    @Column(name = "next_retry_at")
    private Instant nextRetryAt;

    /// HTTP status of the last webhook attempt
    @Column(name = "last_status")
    private Integer lastStatus;

    @Column(name = "last_error", columnDefinition = "text")
    private String lastError;

    @Column(name = "delivered_at")
    private Instant deliveredAt;
}
