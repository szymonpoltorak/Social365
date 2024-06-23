import { Injectable } from '@angular/core';
import { AboutExperienceService } from "@api/profile/about/about-experience.service";
import { AboutContactService } from "@api/profile/about/about-contact.service";
import { Observable } from "rxjs";
import { AboutDetailsRequest } from "@interfaces/profile/about/about-details-request.interface";
import { ProfileRequest } from "@interfaces/profile/profile-request.interface";
import { DetailsType } from "@enums/profile/details-type.enum";
import { AboutDataEmail } from "@interfaces/profile/about/about-data-email.interface";
import { AboutDetailsService } from "@api/profile/about/about-details.service";
import { RelationshipStatusRequest } from "@interfaces/profile/about/relationship-status-request.interface";
import { GenderRequest } from "@interfaces/profile/about/gender-request.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { Either } from "@core/types/feed/either.type";
import { Optional } from "@core/types/profile/optional.type";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";
import { AboutOptionData } from "@interfaces/profile/about/about-option-data.interface";
import { AboutEnumMapperService } from "@api/profile/about/about-enum-mapper.service";

@Injectable({
    providedIn: 'root'
})
export class AboutApiHelperService {

    constructor(private aboutExperienceService: AboutExperienceService,
                private aboutContactService: AboutContactService,
                private localStorage: LocalStorageService,
                private aboutEnumMapperService: AboutEnumMapperService,
                private aboutDetailsService: AboutDetailsService) {
    }

    mapAboutOptionToAboutOptionRequest<T>(detailsValue: Either<string, Date>,
                                          option: AboutOption): T {
        if (option.type === DetailsType.MAIL) {
            return {
                detailsType: option.type,
                profileId: this.localStorage.getUserProfileIdFromStorage(),
                privacyLevel: this.getPrivacyLevel(option.data),
            } as T;
        }
        if (option.type === DetailsType.RELATIONSHIP_STATUS) {
            return {
                detailsType: option.type,
                profileId: this.localStorage.getUserProfileIdFromStorage(),
                relationshipStatus: this.aboutEnumMapperService.mapRelationshipToTypeEnum(detailsValue as string),
                privacyLevel: this.getPrivacyLevel(option.data),
            } as T;
        }
        if (option.type === DetailsType.GENDER) {
            return {
                detailsType: option.type,
                profileId: this.localStorage.getUserProfileIdFromStorage(),
                gender: this.aboutEnumMapperService.mapGenderToEnum(detailsValue as string),
                privacyLevel: this.getPrivacyLevel(option.data),
            } as T;
        }
        return {
            detailsType: option.type,
            profileId: this.localStorage.getUserProfileIdFromStorage(),
            detailsValue: detailsValue as string,
            privacyLevel: this.getPrivacyLevel(option.data),
        } as T;
    }

    updateAboutOption<T extends AboutDataEmail>(data: T): Observable<ProfileRequest> {
        if (data.detailsType === DetailsType.PHONE) {
            return this.aboutContactService.updateProfilePhoneNumber(data as unknown as AboutDetailsRequest);
        }
        if (data.detailsType === DetailsType.MAIL) {
            return this.aboutContactService.updateProfileEmailPrivacyLevel(data.profileId, data.privacyLevel);
        }
        if (data.detailsType === DetailsType.COLLEGE) {
            return this.aboutExperienceService.updateProfileCollege(data as unknown as AboutDetailsRequest);
        }
        if (data.detailsType === DetailsType.HIGH_SCHOOL) {
            return this.aboutExperienceService.updateProfileHighSchool(data as unknown as AboutDetailsRequest);
        }
        if (data.detailsType === DetailsType.RELATIONSHIP_STATUS) {
            return this.aboutDetailsService.updateProfileRelationshipStatus(data as unknown as RelationshipStatusRequest);
        }
        if (data.detailsType === DetailsType.CURRENT_CITY) {
            return this.aboutDetailsService.updateProfileCurrentCity(data as unknown as AboutDetailsRequest);
        }
        if (data.detailsType === DetailsType.GENDER) {
            return this.aboutDetailsService.updateProfileGender(data as unknown as GenderRequest);
        }
        if (data.detailsType === DetailsType.HOMETOWN) {
            return this.aboutDetailsService.updateProfileHomeTown(data as unknown as AboutDetailsRequest);
        }
        throw new Error('Invalid details type');
    }

    deleteAboutOption(detailsType: DetailsType): Observable<ProfileRequest> {
        const profileId: string = this.localStorage.getUserProfileIdFromStorage();

        if (detailsType === DetailsType.PHONE) {
            return this.aboutContactService.deleteProfilePhoneNumber(profileId);
        }
        if (detailsType === DetailsType.COLLEGE) {
            return this.aboutExperienceService.deleteProfileCollege(profileId);
        }
        if (detailsType === DetailsType.HIGH_SCHOOL) {
            return this.aboutExperienceService.deleteProfileHighSchool(profileId);
        }
        if (detailsType === DetailsType.RELATIONSHIP_STATUS) {
            return this.aboutDetailsService.deleteProfileRelationshipStatus(profileId);
        }
        if (detailsType === DetailsType.CURRENT_CITY) {
            return this.aboutDetailsService.deleteProfileCurrentCity(profileId);
        }
        if (detailsType === DetailsType.GENDER) {
            return this.aboutDetailsService.deleteProfileGender(profileId);
        }
        if (detailsType === DetailsType.HOMETOWN) {
            return this.aboutDetailsService.deleteProfileHomeTown(profileId);
        }
        throw new Error('Invalid details type');
    }

    private getPrivacyLevel(data: Optional<AboutOptionData>): PrivacyLevel {
        return data === null ? PrivacyLevel.ONLY_ME : data.privacyLevel;
    }

}
