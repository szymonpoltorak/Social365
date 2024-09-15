package razepl.dev.social365.notifications.gateway.config.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true
)
public class SecurityConfiguration {

    private static final String FRONTEND_URL = "http://reverse-proxy:80";
    private static final String API_PATTERN = "/api/v1/**";

    private final String jwkSetUri;

    public SecurityConfiguration(@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String jwkSetUri) {
        this.jwkSetUri = jwkSetUri;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/actuator/**", "/notifications/**")
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
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt -> jwt.decoder(jwtDecoder())
                ))
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration cors = new CorsConfiguration();

        cors.setAllowedHeaders(List.of(
                HttpHeaders.ACCEPT_ENCODING,
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.USER_AGENT,
                HttpHeaders.ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS
        ));
        cors.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
        ));
        cors.setAllowedOrigins(List.of(
                "*"
        ));

        source.registerCorsConfiguration(API_PATTERN, cors);

        return source;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).jwsAlgorithm(SignatureAlgorithm.RS512).build();
    }

    private String buildContentPolicyDirective() {
        return String.format("form-action 'self' %s; img-src 'self' %s; child-src 'none'; script-src 'self'", FRONTEND_URL, FRONTEND_URL);
    }

}
