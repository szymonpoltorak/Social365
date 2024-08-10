import { DetailsType } from "@enums/profile/details-type.enum";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

export interface AboutDataEmail {
    privacyLevel: PrivacyLevel;
    detailsType: DetailsType;
}
