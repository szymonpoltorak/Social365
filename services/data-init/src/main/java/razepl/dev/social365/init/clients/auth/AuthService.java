package razepl.dev.social365.init.clients.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import razepl.dev.social365.init.clients.auth.constants.AuthMappings;
import razepl.dev.social365.init.clients.auth.data.AuthResponse;
import razepl.dev.social365.init.clients.auth.data.RegisterRequest;

@FunctionalInterface
@FeignClient(name = "AUTH-SERVICE", url = "http://auth-service:8080")
public interface AuthService {

    @PostMapping(value = AuthMappings.REGISTER_MAPPING)
    AuthResponse registerUser(@RequestBody RegisterRequest registerRequest);

}
