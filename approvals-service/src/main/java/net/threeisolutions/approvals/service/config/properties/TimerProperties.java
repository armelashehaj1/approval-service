package net.threeisolutions.approvals.service.config.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Data
@Validated
@ConfigurationProperties("approvals.timers")
public class TimerProperties {
    @NotNull
    private Duration pollInterval;
    @Min(1)
    private int batchSize;
    /// How long a claimed timer stays locked before another pod may retry it
    @NotNull
    private Duration lockDuration;
}
