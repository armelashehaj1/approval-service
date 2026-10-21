package net.threeisolutions.approvals.api.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.DirectoryType;

import java.time.Duration;

/// Where approvers are looked up
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryConfigDto {

    @NotNull
    private DirectoryType type;

    @Size(max = 500)
    private String baseUrl;

    @Valid
    @Builder.Default
    private OutboundAuthDto auth = OutboundAuthDto.builder().build();

    @NotNull
    @Builder.Default
    private Duration cacheTtl = Duration.ofMinutes(5);

    @JsonIgnore
    @AssertTrue(message = "HTTP directory needs baseUrl")
    public boolean isComplete() {
        return type != DirectoryType.HTTP || baseUrl != null;
    }
}
