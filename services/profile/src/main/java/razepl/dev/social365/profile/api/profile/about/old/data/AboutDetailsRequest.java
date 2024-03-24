package razepl.dev.social365.profile.api.profile.about.old.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Builder
public record AboutDetailsRequest(String profileId, PrivacyLevel privacyLevel, String detailsValue) {
}
