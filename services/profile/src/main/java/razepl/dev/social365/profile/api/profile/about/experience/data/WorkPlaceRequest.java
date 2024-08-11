package razepl.dev.social365.profile.api.profile.about.experience.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Builder
public record WorkPlaceRequest(String position, String workplace, PrivacyLevel privacyLevel) {
}
