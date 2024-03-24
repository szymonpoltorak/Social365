package razepl.dev.social365.profile.api.profile.about.old.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

import java.time.LocalDate;

@Builder
public record DateOfBirthRequest(String profileId, LocalDate dateOfBirth, PrivacyLevel privacyLevel) {
}
