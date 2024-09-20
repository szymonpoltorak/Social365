package razepl.dev.social365.init.clients.api.data;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RegisterRequest(String firstName, String lastName, String username, String password, String dateOfBirth) {
}
