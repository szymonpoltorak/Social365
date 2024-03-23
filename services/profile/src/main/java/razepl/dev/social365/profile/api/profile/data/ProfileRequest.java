package razepl.dev.social365.profile.api.profile.data;

import java.time.LocalDate;

public record ProfileRequest(String userId, String name, String lastName, LocalDate dateOfBirth) {
}
