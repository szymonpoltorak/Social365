import { AboutDataEmail } from "@interfaces/profile/about/about-data-email.interface";
import { RelationshipType } from "@enums/api/profile/about/relationship-type.enum";

export interface RelationshipStatusRequest extends AboutDataEmail {
    relationshipStatus: RelationshipType;
}
