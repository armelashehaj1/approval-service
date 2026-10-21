package net.threeisolutions.approvals.api.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.OutboundAuthMode;

/// Credentials approvals uses when calling the client. Secrets are never stored inline, only as secretRef
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboundAuthDto {

    @NotNull
    @Builder.Default
    private OutboundAuthMode mode = OutboundAuthMode.NONE;

    @Size(max = 500)
    private String tokenUri;

    @Size(max = 200)
    private String clientId;

    @Size(max = 200)
    private String scope;

    @Size(max = 200)
    private String secretRef;

    @JsonIgnore
    @AssertTrue(message = "OAUTH_CC needs tokenUri, clientId and secretRef; API_KEY needs secretRef")
    public boolean isComplete() {
        return switch (mode == null ? OutboundAuthMode.NONE : mode) {
            case NONE -> true;
            case OAUTH_CC -> tokenUri != null && clientId != null && secretRef != null;
            case API_KEY -> secretRef != null;
        };
    }
}
