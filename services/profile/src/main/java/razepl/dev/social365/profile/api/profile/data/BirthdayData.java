package razepl.dev.social365.profile.api.profile.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;

@Builder
public record BirthdayData(String friendId, BirthDate birthDate) {
}
