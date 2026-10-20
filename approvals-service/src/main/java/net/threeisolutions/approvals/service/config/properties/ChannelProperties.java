package net.threeisolutions.approvals.service.config.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.List;

@Data
@Validated
@ConfigurationProperties("approvals.channels")
public class ChannelProperties {
    @NotEmpty
    private List<String> defaultChannels;
    @NotNull
    private Duration tokenTtl;
    @NotBlank
    private String publicBaseUrl;
}
