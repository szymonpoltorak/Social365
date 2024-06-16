import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

export interface DateOfBirthRequest {
    profileId: string;
    dateOfBirth: Date;
    privacyLevel: PrivacyLevel;
}
