package razepl.dev.social365.profile.api.profile.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.profile.Profile;

@Builder
public record BirthdayData(Profile friend, BirthDate birthDate) {
}
