package net.threeisolutions.approvals.api.dto.client;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.threeisolutions.approvals.api.enums.ChannelType;

import java.util.ArrayList;
import java.util.List;

/// Everything that varies per consuming project
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientConfigDto {

    @NotNull
    @Valid
    private ClientAuthDto auth;

    /// Rulesets the client may reference. Empty means none: only inline policies, if allowed
    @Builder.Default
    private List<@NotBlank String> rulesets = new ArrayList<>();

    private boolean allowInlinePolicy;

    @NotNull
    @Valid
    private DirectoryConfigDto directory;

    @NotNull
    @Valid
    private DeliveryConfigDto delivery;

    /// Channels used when a policy names none. Empty means the platform default.
    @Builder.Default
    private List<@NotNull ChannelType> channels = new ArrayList<>();

    @Valid
    @Builder.Default
    private UiConfigDto ui = UiConfigDto.builder().build();

    public boolean mayUse(String ruleset) {
        return rulesets != null && rulesets.contains(ruleset);
    }
}
