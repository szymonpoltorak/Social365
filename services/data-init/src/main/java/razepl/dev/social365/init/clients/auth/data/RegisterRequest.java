package razepl.dev.social365.init.clients.auth.data;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RegisterRequest(String firstName, String lastName, String username, String password, LocalDate dateOfBirth) {
}
