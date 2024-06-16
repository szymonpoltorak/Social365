import { Gender } from "@enums/profile/gender.enum";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

export interface GenderRequest {
    profileId: string;
    gender: Gender;
    privacyLevel: PrivacyLevel;
}
