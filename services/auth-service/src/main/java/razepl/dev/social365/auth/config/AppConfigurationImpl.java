package razepl.dev.social365.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import razepl.dev.social365.auth.config.interfaces.AppConfiguration;
import razepl.dev.social365.auth.entities.user.interfaces.UserRepository;

import java.security.SecureRandom;
import java.util.List;

import static razepl.dev.social365.auth.config.constants.CorsConfig.ALLOWED_REQUESTS;
import static razepl.dev.social365.auth.config.constants.CorsConfig.API_PATTERN;
import static razepl.dev.social365.auth.config.constants.CorsConfig.CONTENT_TYPE_HEADER;
import static razepl.dev.social365.auth.config.constants.CorsConfig.CORS_ADDRESSES;
import static razepl.dev.social365.auth.config.constants.Headers.AUTH_HEADER;

@RequiredArgsConstructor
@Configuration
public class AppConfigurationImpl implements AppConfiguration {

    private static final int BCRYPT_STRENGTH = 10;
    private final UserRepository userRepository;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Bean
    @Override
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        configuration.setAllowedOrigins(CORS_ADDRESSES);
        configuration.setAllowedMethods(ALLOWED_REQUESTS);
        configuration.setAllowedHeaders(List.of(AUTH_HEADER, CONTENT_TYPE_HEADER));

        source.registerCorsConfiguration(API_PATTERN, configuration);

        return source;
    }

    @Bean
    @Override
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    @Override
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y,
                BCRYPT_STRENGTH, new SecureRandom(SecureRandom.getSeed(BCRYPT_STRENGTH)));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
