import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

export interface WorkPlaceRequest {
    profileId: string;
    position: string;
    workplace: string;
    privacyLevel: PrivacyLevel;
}
