package net.threeisolutions.approvals.service.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ClientAuthentication extends AbstractAuthenticationToken {

    private final ClientContext client;

    public ClientAuthentication(ClientContext client, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.client = client;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public ClientContext getPrincipal() {
        return client;
    }

    @Override
    public String getName() {
        return client.getCode();
    }
}
