package razepl.dev.social365.profile.api.profile.data;

import java.time.LocalDate;

public record ProfileRequest(long userId, String username, String name, String lastName, LocalDate dateOfBirth) {
}
