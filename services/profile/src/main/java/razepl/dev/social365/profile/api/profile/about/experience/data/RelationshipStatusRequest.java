package razepl.dev.social365.profile.api.profile.about.experience.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.about.relationship.enums.RelationshipStatusType;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Builder
public record RelationshipStatusRequest(RelationshipStatusType relationshipStatus, PrivacyLevel privacyLevel) {
}
