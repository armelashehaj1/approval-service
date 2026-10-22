package net.threeisolutions.approvals.service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.service.util.exception.ApiException;
import net.threeisolutions.approvals.service.util.exception.UserNotAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class UserClientContextFilter extends OncePerRequestFilter {

    private final RequestMatcher userPaths;
    private final ClientContextResolver clientResolver;
    private final UserContextResolver userResolver;
    private final ProblemResponses problems;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean userPath = userPaths.matches(request);
        try {
            // User paths act for a person, every other path for a calling system
            // the JWT is swapped for an authentication whose principal is the resolved context
            switch (authentication) {
                case JwtAuthenticationToken jwt when userPath -> SecurityContextHolder.getContext().setAuthentication(
                        new UserAuthentication(userResolver.resolve(jwt.getToken()), jwt.getAuthorities()));
                case JwtAuthenticationToken jwt -> SecurityContextHolder.getContext().setAuthentication(
                        new ClientAuthentication(clientResolver.resolve(jwt.getToken()), jwt.getAuthorities()));
                case ClientAuthentication ignored when userPath ->
                        throw new UserNotAuthorizedException("A user token is required");
                case null, default -> {
                }
            }
        } catch (ApiException e) {
            problems.write(response, e.getStatus(), e.getCode(), e.getMessage());
            return;
        }
        chain.doFilter(request, response);
    }
}
