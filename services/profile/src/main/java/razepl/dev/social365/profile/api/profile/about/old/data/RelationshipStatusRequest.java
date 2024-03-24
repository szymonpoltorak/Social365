package razepl.dev.social365.profile.api.profile.about.old.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.about.relationship.enums.RelationshipStatusType;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Builder
public record RelationshipStatusRequest(String profileId, RelationshipStatusType relationshipStatus,
                                        PrivacyLevel privacyLevel) {
}
