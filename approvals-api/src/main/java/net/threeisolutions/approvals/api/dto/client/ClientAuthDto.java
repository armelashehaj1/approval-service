package net.threeisolutions.approvals.api.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.ClientAuthMode;

/// How the client authenticates to approvals
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAuthDto {

    @NotNull
    private ClientAuthMode mode;

    @Size(max = 500)
    private String issuer;

    @Size(max = 200)
    private String clientId;

    @JsonIgnore
    @AssertTrue(message = "OAUTH_CC needs issuer")
    public boolean isComplete() {
        return mode != ClientAuthMode.OAUTH_CC || issuer != null;
    }
}
