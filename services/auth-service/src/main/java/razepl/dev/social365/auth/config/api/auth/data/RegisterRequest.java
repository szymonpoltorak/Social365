package razepl.dev.social365.auth.config.api.auth.data;

import lombok.Builder;
import razepl.dev.social365.auth.entities.user.interfaces.Password;

@Builder
public record RegisterRequest(String name, String surname, String username, @Password String password) {
}
