package net.threeisolutions.approvals.service.config;

import net.threeisolutions.approvals.service.config.properties.ApprovalsSecurityProperties;
import net.threeisolutions.approvals.service.dal.repository.ApprovalClientRepository;
import net.threeisolutions.approvals.service.dal.repository.ApprovalClientSecretRepository;
import net.threeisolutions.approvals.service.security.ApiKeyAuthenticationFilter;
import net.threeisolutions.approvals.service.security.ClientContextResolver;
import net.threeisolutions.approvals.service.security.PermissionClaimsConverter;
import net.threeisolutions.approvals.service.security.ProblemResponses;
import net.threeisolutions.approvals.service.security.Scopes;
import net.threeisolutions.approvals.service.security.UserClientContextFilter;
import net.threeisolutions.approvals.service.security.UserContextResolver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import tools.jackson.databind.json.JsonMapper;

@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    /// Paths where a person acts
    static final RequestMatcher USER_PATHS = PathPatternRequestMatcher.withDefaults().matcher("/api/v1/tasks/**");

    @Bean
    ProblemResponses problemResponses(JsonMapper jsonMapper) {
        return new ProblemResponses(jsonMapper);
    }

    /// Maps the deployments permission claims to `SCOPE_*` and names the principal after the configured claim
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(ApprovalsSecurityProperties properties) {
        ApprovalsSecurityProperties.OAuth oauth = properties.getOauth();
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new PermissionClaimsConverter(oauth.getPermissionClaims()));
        converter.setPrincipalClaimName(oauth.getPrincipalClaim());
        return converter;
    }

    /// Admin API and actuator
    @Bean
    @Order(1)
    SecurityFilterChain adminChain(HttpSecurity http, ProblemResponses problems, ObjectProvider<JwtDecoder> jwtDecoder,
                                   JwtAuthenticationConverter jwtConverter) throws Exception {
        stateless(http, problems)
                .securityMatcher("/admin/**", "/actuator/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health/**").permitAll()
                        .anyRequest().hasAuthority(Scopes.ADMIN));
        return jwt(http, jwtDecoder, jwtConverter, problems).build();
    }

    /// API key or a JWT from the deployments IdP, checked against the scope of each path
    /// User paths then resolve the person, every other path a known, enabled client
    @Bean
    @Order(2)
    SecurityFilterChain apiChain(HttpSecurity http, ApprovalsSecurityProperties properties,
                                 ApprovalClientRepository clients, ApprovalClientSecretRepository secrets,
                                 ClientContextResolver clientResolver, UserContextResolver userResolver,
                                 ProblemResponses problems, ObjectProvider<JwtDecoder> jwtDecoder,
                                 JwtAuthenticationConverter jwtConverter) throws Exception {
        stateless(http, problems)
                .securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(USER_PATHS).hasAuthority(Scopes.TASKS)
                        .requestMatchers(HttpMethod.GET, "/api/v1/requests/**", "/api/v1/documents/**")
                        .hasAnyAuthority(Scopes.REQUESTS_READ, Scopes.REQUESTS_WRITE)
                        .requestMatchers("/api/v1/requests/**", "/api/v1/documents/**")
                        .hasAuthority(Scopes.REQUESTS_WRITE)
                        .anyRequest().authenticated())
                .addFilterBefore(new ApiKeyAuthenticationFilter(properties.getApiKeyHeader(), clients, secrets, problems),
                        BearerTokenAuthenticationFilter.class)
                .addFilterAfter(new UserClientContextFilter(USER_PATHS, clientResolver, userResolver, problems),
                        AuthorizationFilter.class);
        return jwt(http, jwtDecoder, jwtConverter, problems).build();
    }

    /// Everything else is closed until a later phase opens it (/a/**, /internal/**)
    @Bean
    @Order(3)
    SecurityFilterChain defaultChain(HttpSecurity http, ProblemResponses problems) throws Exception {
        return stateless(http, problems)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/error").permitAll().anyRequest().denyAll())
                .build();
    }

    private static HttpSecurity stateless(HttpSecurity http, ProblemResponses problems) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint(problems.entryPoint())
                        .accessDeniedHandler(problems.accessDeniedHandler()));
    }

    /// Bearer tokens are accepted only when an issuer is configured
    /// Boot's decoder checks signature, expiry, iss and aud
    private static HttpSecurity jwt(HttpSecurity http, ObjectProvider<JwtDecoder> jwtDecoder,
                                    JwtAuthenticationConverter jwtConverter, ProblemResponses problems) throws Exception {
        JwtDecoder decoder = jwtDecoder.getIfAvailable();
        if (decoder != null) {
            // The bearer filter reports invalid tokens through its own entry point
            http.oauth2ResourceServer(oauth -> oauth
                    .jwt(jwt -> jwt.decoder(decoder).jwtAuthenticationConverter(jwtConverter))
                    .authenticationEntryPoint(problems.entryPoint()));
        }
        return http;
    }
}
