package razepl.dev.social365.auth.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtKeyService;
import razepl.dev.social365.auth.entities.user.interfaces.UserRepository;
import razepl.dev.social365.auth.exceptions.auth.throwable.UserDoesNotExistException;

import java.security.SecureRandom;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppConfiguration {

    private static final String API_PATTERN = "/api/v1/**";
    private static final int BCRYPT_STRENGTH = 10;

    private final UserRepository userRepository;
    private final JwtKeyService jwtKeyService;

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        return jwtKeyService.getJwkSource();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(username));
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
                SecurityConfiguration.FRONTEND_URL,
                "http://localhost:80"
        ));

        source.registerCorsConfiguration(API_PATTERN, cors);

        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(
                BCryptPasswordEncoder.BCryptVersion.$2Y,
                BCRYPT_STRENGTH,
                new SecureRandom(SecureRandom.getSeed(BCRYPT_STRENGTH))
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
