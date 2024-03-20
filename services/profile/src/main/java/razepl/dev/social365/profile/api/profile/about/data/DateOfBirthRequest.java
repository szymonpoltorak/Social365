package razepl.dev.social365.profile.api.profile.about.data;

import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

import java.time.LocalDate;

public record DateOfBirthRequest(String username, LocalDate dateOfBirth, PrivacyLevel privacyLevel) {
}
