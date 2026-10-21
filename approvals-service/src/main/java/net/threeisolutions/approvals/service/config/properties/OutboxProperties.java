package net.threeisolutions.approvals.service.config.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

/// Transactional outbox relay to Kafka
@Data
@Validated
@ConfigurationProperties("approvals.outbox")
public class OutboxProperties {

    @NotNull
    private Duration pollInterval;


    @Min(1)
    private int batchSize;
}