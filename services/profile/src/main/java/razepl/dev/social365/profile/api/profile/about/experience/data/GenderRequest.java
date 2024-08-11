package razepl.dev.social365.profile.api.profile.about.experience.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.about.gender.enums.GenderType;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Builder
public record GenderRequest(GenderType gender, PrivacyLevel privacyLevel) {
}
