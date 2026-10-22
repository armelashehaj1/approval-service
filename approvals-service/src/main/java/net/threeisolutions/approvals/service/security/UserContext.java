package net.threeisolutions.approvals.service.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {
    /// Value of the configured principal claim (Entra: `oid`; others: `sub`)
    private String subject;
    private String issuer;
    private String email;
    private String name;
    private boolean mfa;
    /// Token claims kept with a decision
    private Map<String, Object> authContext;
}
