import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, take } from "rxjs";
import { ProfileRequest } from "@interfaces/profile/profile-request.interface";
import { WorkPlaceRequest } from "@interfaces/profile/about/workplace-request.interface";
import { AboutExperienceMappings } from "@enums/api/profile/about/about-experience-mappings.enum";
import { AboutDetailsRequest } from "@interfaces/profile/about/about-details-request.interface";

@Injectable({
    providedIn: 'root'
})
export class AboutExperienceService {

    constructor(private http: HttpClient) {
    }

    updateProfileWorkPlace(workPlaceRequest: WorkPlaceRequest): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(AboutExperienceMappings.UPDATE_PROFILE_WORK_PLACE_MAPPING,
            workPlaceRequest).pipe(take(1));
    }

    updateProfileCollege(educationRequest: AboutDetailsRequest): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(AboutExperienceMappings.UPDATE_PROFILE_EDUCATION_MAPPING,
            educationRequest).pipe(take(1));
    }

    updateProfileHighSchool(highSchoolRequest: AboutDetailsRequest): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(AboutExperienceMappings.UPDATE_PROFILE_HIGH_SCHOOL_MAPPING,
            highSchoolRequest).pipe(take(1));
    }

    deleteProfileWorkPlace(): Observable<ProfileRequest> {
        return this.http.delete<ProfileRequest>(AboutExperienceMappings.DELETE_PROFILE_WORK_PLACE_MAPPING).pipe(take(1));
    }

    deleteProfileCollege(): Observable<ProfileRequest> {
        return this.http.delete<ProfileRequest>(AboutExperienceMappings.DELETE_PROFILE_EDUCATION_MAPPING).pipe(take(1));
    }

    deleteProfileHighSchool(): Observable<ProfileRequest> {
        return this.http.delete<ProfileRequest>(AboutExperienceMappings.DELETE_PROFILE_HIGH_SCHOOL_MAPPING).pipe(take(1));
    }
}
