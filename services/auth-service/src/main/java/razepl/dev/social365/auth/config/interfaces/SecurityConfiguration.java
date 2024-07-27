package razepl.dev.social365.auth.config.interfaces;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@FunctionalInterface
public interface SecurityConfiguration {

    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception;

}
