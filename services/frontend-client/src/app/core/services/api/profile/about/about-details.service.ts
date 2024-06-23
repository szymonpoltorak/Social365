import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, take } from "rxjs";
import { ProfileRequest } from "@interfaces/profile/profile-request.interface";
import { DateOfBirthRequest } from "@interfaces/profile/about/date-of-birth-request.interface";
import { AboutDetailsMappings } from "@enums/api/profile/about/about-details-mappings.enum";
import { GenderRequest } from "@interfaces/profile/about/gender-request.interface";
import { RelationshipStatusRequest } from "@interfaces/profile/about/relationship-status-request.interface";
import { AboutDetailsRequest } from "@interfaces/profile/about/about-details-request.interface";

@Injectable({
    providedIn: 'root'
})
export class AboutDetailsService {

    constructor(private http: HttpClient) {
    }

    updateProfileDateOfBirth(dateOfBirthRequest: DateOfBirthRequest): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(AboutDetailsMappings.UPDATE_PROFILE_DATE_OF_BIRTH_MAPPING,
            dateOfBirthRequest).pipe(take(1));
    }

    updateProfileGender(genderRequest: GenderRequest): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(AboutDetailsMappings.UPDATE_PROFILE_GENDER_MAPPING,
            genderRequest).pipe(take(1));
    }

    deleteProfileGender(profileId: string): Observable<ProfileRequest> {
        return this.http.delete<ProfileRequest>(AboutDetailsMappings.DELETE_PROFILE_GENDER_MAPPING, {
            params: {
                profileId: profileId
            }
        }).pipe(take(1));
    }

    updateProfileRelationshipStatus(relationshipStatusRequest: RelationshipStatusRequest): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(AboutDetailsMappings.UPDATE_PROFILE_RELATIONSHIP_STATUS_MAPPING,
            relationshipStatusRequest).pipe(take(1));
    }

    deleteProfileRelationshipStatus(profileId: string): Observable<ProfileRequest> {
        return this.http.delete<ProfileRequest>(AboutDetailsMappings.DELETE_PROFILE_RELATIONSHIP_STATUS_MAPPING, {
            params: {
                profileId: profileId
            }
        }).pipe(take(1));
    }

    updateProfileCurrentCity(currentCity: AboutDetailsRequest): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(AboutDetailsMappings.UPDATE_PROFILE_CURRENT_CITY_MAPPING,
            currentCity).pipe(take(1));
    }

    deleteProfileCurrentCity(profileId: string): Observable<ProfileRequest> {
        return this.http.delete<ProfileRequest>(AboutDetailsMappings.DELETE_PROFILE_CURRENT_CITY_MAPPING, {
            params: {
                profileId: profileId
            }
        }).pipe(take(1));
    }

    updateProfileHomeTown(homeTown: AboutDetailsRequest): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(AboutDetailsMappings.UPDATE_PROFILE_HOME_TOWN_MAPPING,
            homeTown).pipe(take(1));
    }

    deleteProfileHomeTown(profileId: string): Observable<ProfileRequest> {
        return this.http.delete<ProfileRequest>(AboutDetailsMappings.DELETE_PROFILE_HOME_TOWN_MAPPING, {
            params: {
                profileId: profileId
            }
        }).pipe(take(1));
    }
}
