package razepl.dev.social365.init.clients.api.data;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ProfileRequest(long userId, String username, String firstName, String lastName, LocalDate dateOfBirth) {
}
