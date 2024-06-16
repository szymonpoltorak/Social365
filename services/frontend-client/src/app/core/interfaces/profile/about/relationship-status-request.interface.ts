import { PrivacyLevel } from "@enums/profile/privacy-level.enum";
import { Relationship } from "@enums/profile/relationship.enum";

export interface RelationshipStatusRequest {
    profileId: string;
    relationshipStatus: Relationship;
    privacyLevel: PrivacyLevel;
}
