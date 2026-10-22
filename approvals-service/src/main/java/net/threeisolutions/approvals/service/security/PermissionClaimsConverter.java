package net.threeisolutions.approvals.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/// Turns every value of the configured permission claims into a `SCOPE_<value>` authority
@RequiredArgsConstructor
public class PermissionClaimsConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final List<String> claims;

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        for (String claim : claims) {
            values(jwt.getClaim(claim))
                    .filter(StringUtils::hasText)
                    .map(value -> new SimpleGrantedAuthority(Scopes.PREFIX + value))
                    .forEach(authorities::add);
        }
        return authorities;
    }

    private static Stream<String> values(Object claim) {
        return switch (claim) {
            case String s -> Arrays.stream(s.split(" "));
            case Collection<?> c -> c.stream().map(String::valueOf);
            case null, default -> Stream.empty();
        };
    }
}
