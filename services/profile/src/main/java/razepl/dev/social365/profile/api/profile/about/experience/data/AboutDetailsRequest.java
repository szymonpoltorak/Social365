package razepl.dev.social365.profile.api.profile.about.experience.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.about.details.enums.DetailsType;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Builder
public record AboutDetailsRequest(PrivacyLevel privacyLevel, String detailsValue, DetailsType detailsType) {
}
