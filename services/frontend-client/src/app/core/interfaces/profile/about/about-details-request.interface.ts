import { PrivacyLevel } from "@enums/profile/privacy-level.enum";
import { DetailsType } from "@enums/api/profile/about/details-type.enum";

export interface AboutDetailsRequest {
    profileId: string;
    privacyLevel: PrivacyLevel;
    detailsValue: string;
    detailsType: DetailsType;
}
