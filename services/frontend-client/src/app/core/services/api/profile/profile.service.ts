import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, take } from "rxjs";
import { ProfileSummary } from "@interfaces/feed/profile-summary.interface";
import { ProfileMappings } from "@enums/api/profile/profile-mappings.enum";
import { Profile } from "@interfaces/feed/profile.interface";
import { BirthdayInfo } from "@interfaces/feed/birthday-info.interface";
import { Page } from "@interfaces/feed/page.interface";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    constructor(private http: HttpClient) {
    }

    getTodayBirthdays(profileId: string, pageNumber: number): Observable<Page<BirthdayInfo>> {
        return this.http.get<Page<BirthdayInfo>>(ProfileMappings.GET_TODAY_BIRTHDAYS_MAPPING, {
            params: {
                profileId: profileId,
                pageNumber: pageNumber
            }
        }).pipe(take(1));
    }

    getProfileSummary(profileId: string): Observable<ProfileSummary> {
        return this.http.get<ProfileSummary>(ProfileMappings.GET_PROFILE_SUMMARY_MAPPING, {
            params: {
                profileId: profileId
            }
        }).pipe(take(1));
    }

    getBasicProfileInfo(profileId: string): Observable<Profile> {
        return this.http.get<Profile>(ProfileMappings.GET_BASIC_PROFILE_INFO_MAPPING, {
            params: {
                profileId: profileId
            }
        }).pipe(take(1));
    }

    updateProfileBio(profileId: string, bio: string): Observable<Profile> {
        return this.http.put<Profile>(ProfileMappings.UPDATE_PROFILE_BIO_MAPPING, {
            profileId: profileId,
            bio: bio
        }).pipe(take(1));
    }

}
