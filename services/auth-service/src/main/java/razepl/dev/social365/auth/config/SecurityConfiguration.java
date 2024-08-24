package razepl.dev.social365.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import razepl.dev.social365.auth.config.jwt.constants.JwtClaims;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtAuthenticationFilter;

import java.util.UUID;

import static razepl.dev.social365.auth.config.constants.Matchers.AUTH_MATCHERS;
import static razepl.dev.social365.auth.config.constants.Matchers.LOGOUT_URL;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfiguration {

    static final String FRONTEND_URL = "http://reverse-proxy:80";

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);

        httpSecurity
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        httpSecurity
                .exceptionHandling(exceptions -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));

        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   @Value("${spring.application.name}") String applicationName) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(LOGOUT_URL)
                        .permitAll()
                        .requestMatchers(AUTH_MATCHERS)
                        .permitAll()
                        .requestMatchers("/actuator/**", "/oauth2/jwks")
                        .permitAll()
                        .requestMatchers(
                                "/%s/swagger-ui/**".formatted(applicationName),
                                "/%s/v3/api-docs/**".formatted(applicationName)
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .cors(Customizer.withDefaults())
                .headers(headers -> headers
                        .xssProtection(Customizer.withDefaults())
                        .contentSecurityPolicy(policy -> policy
                                .policyDirectives(buildContentPolicyDirective())
                        )
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl(LOGOUT_URL)
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()
                        )
                )
                .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("Social365")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.PRIVATE_KEY_JWT)
                .authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
                .postLogoutRedirectUri("http://localhost/auth/login")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.EMAIL)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        return new InMemoryRegisteredClientRepository(oidcClient);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings
                .builder()
                .issuer(JwtClaims.TOKEN_ISSUER)
                .build();
    }

    private String buildContentPolicyDirective() {
        return String.format("form-action 'self' %s; img-src 'self'; child-src 'none'; script-src 'self'", FRONTEND_URL);
    }
}
