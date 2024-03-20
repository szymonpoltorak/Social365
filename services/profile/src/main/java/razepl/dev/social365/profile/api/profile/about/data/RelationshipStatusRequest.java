package razepl.dev.social365.profile.api.profile.about.data;

import razepl.dev.social365.profile.nodes.about.relationship.enums.RelationshipStatusType;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

public record RelationshipStatusRequest(String username, RelationshipStatusType relationshipStatus,
                                        PrivacyLevel privacyLevel) {
}
