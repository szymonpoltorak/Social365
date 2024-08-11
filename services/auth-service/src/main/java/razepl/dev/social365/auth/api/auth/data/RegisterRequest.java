package razepl.dev.social365.auth.api.auth.data;

import lombok.Builder;
import razepl.dev.social365.auth.entities.user.interfaces.Password;

import java.time.LocalDate;

@Builder
public record RegisterRequest(String firstName, String lastName, String username, @Password String password, LocalDate dateOfBirth) {
}
