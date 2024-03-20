package razepl.dev.social365.profile.api.profile.about.data;

import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

public record WorkPlaceRequest(String username, String position, String workplace, PrivacyLevel privacyLevel) {
}
