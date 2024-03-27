package razepl.dev.social365.profile.api.profile.about.overview.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Builder
public record AboutOptionResponse(String label, PrivacyLevel privacyLevel) {
}
