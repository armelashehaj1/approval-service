package net.threeisolutions.approvals.service.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication extends AbstractAuthenticationToken {

    private final UserContext user;

    public UserAuthentication(UserContext user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserContext getPrincipal() {
        return user;
    }

    @Override
    public String getName() {
        return user.getSubject();
    }
}
