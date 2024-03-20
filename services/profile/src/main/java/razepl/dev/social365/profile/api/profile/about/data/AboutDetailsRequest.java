package razepl.dev.social365.profile.api.profile.about.data;

import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

public record AboutDetailsRequest(String username, PrivacyLevel privacyLevel, String detailsValue) {
}
