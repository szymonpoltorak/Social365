import { DetailsType } from "@enums/profile/details-type.enum";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

export interface AboutDataEmail {
    profileId: string;
    privacyLevel: PrivacyLevel;
    detailsType: DetailsType;
}
