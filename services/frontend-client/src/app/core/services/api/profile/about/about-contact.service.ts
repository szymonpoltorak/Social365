import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AboutDetailsRequest } from "@interfaces/profile/about/about-details-request.interface";
import { Observable, take } from "rxjs";
import { ProfileRequest } from "@interfaces/profile/profile-request.interface";
import { AboutContactMappings } from "@enums/api/profile/about/about-contact-mappings.enum";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

@Injectable({
    providedIn: 'root'
})
export class AboutContactService {

    constructor(private http: HttpClient) {
    }

    updateProfilePhoneNumber(phoneNumberRequest: AboutDetailsRequest): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(AboutContactMappings.UPDATE_PROFILE_PHONE_NUMBER_MAPPING,
            phoneNumberRequest).pipe(take(1));
    }

    updateProfileEmailPrivacyLevel(profileId: string, privacyLevel: PrivacyLevel): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(AboutContactMappings.UPDATE_PROFILE_EMAIL_PRIVACY_LEVEL_MAPPING, {
            profileId: profileId,
            privacyLevel: privacyLevel
        }).pipe(take(1));
    }

    deleteProfilePhoneNumber(profileId: string): Observable<ProfileRequest> {
        return this.http.delete<ProfileRequest>(AboutContactMappings.DELETE_PROFILE_PHONE_NUMBER_MAPPING, {
            params: {
                profileId: profileId
            }
        }).pipe(take(1));
    }

}
