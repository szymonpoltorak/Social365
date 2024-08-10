import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

export interface WorkPlaceRequest {
    position: string;
    workplace: string;
    privacyLevel: PrivacyLevel;
}
