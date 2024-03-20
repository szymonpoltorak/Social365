package razepl.dev.social365.profile.api.profile.about.data;

import razepl.dev.social365.profile.nodes.about.gender.enums.GenderType;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

public record GenderRequest(String username, GenderType gender, PrivacyLevel privacyLevel) {
}
