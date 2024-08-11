import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, take } from "rxjs";
import { ProfileSummary } from "@interfaces/feed/profile-summary.interface";
import { ProfileMappings } from "@enums/api/profile/profile-mappings.enum";
import { Profile } from "@interfaces/feed/profile.interface";
import { BirthdayInfo } from "@interfaces/feed/birthday-info.interface";
import { Page } from "@interfaces/utils/page.interface";
import { ProfileRequest } from "@interfaces/profile/profile-request.interface";
import { ProfileQuery } from "@interfaces/feed/profile-query.interface";
import { ProfileSearch } from "@interfaces/search/profile-search.interface";
import { ProfileBasicInfo } from "@interfaces/profile/profile-basic-info.interface";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    constructor(private http: HttpClient) {
    }

    getTodayBirthdays(pageNumber: number): Observable<Page<BirthdayInfo>> {
        return this.http.get<Page<BirthdayInfo>>(ProfileMappings.GET_TODAY_BIRTHDAYS_MAPPING, {
            params: {
                pageNumber: pageNumber
            }
        }).pipe(take(1));
    }

    getProfilesByPattern(pattern: string, pageNumber: number, pageSize: number): Observable<Page<ProfileQuery>> {
        return this.http.get<Page<ProfileQuery>>(ProfileMappings.GET_PROFILES_BY_PATTERN_MAPPING, {
            params: {
                pattern: pattern,
                pageNumber: pageNumber,
                pageSize: pageSize
            }
        }).pipe(take(1));
    }

    getProfileSummary(): Observable<ProfileSummary> {
        return this.http.get<ProfileSummary>(ProfileMappings.GET_PROFILE_SUMMARY_MAPPING).pipe(take(1));
    }

    getBasicProfileInfo(): Observable<Profile> {
        return this.http.get<Profile>(ProfileMappings.GET_BASIC_PROFILE_INFO_MAPPING).pipe(take(1));
    }

    updateProfileBio(profileId: string, bio: string): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(ProfileMappings.UPDATE_PROFILE_BIO_MAPPING, {}, {
            params: {
                bio: bio
            }
        }).pipe(take(1));
    }

    getBasicProfileInfoByUsername(username: string): Observable<ProfileBasicInfo> {
        return this.http.get<ProfileBasicInfo>(ProfileMappings.GET_BASIC_PROFILE_INFO_BY_USERNAME_MAPPING, {
            params: {
                username: username
            }
        }).pipe(take(1));
    }

    getProfilesSearchByPattern(pattern: string, pageNumber: number, pageSize: number): Observable<Page<ProfileSearch>> {
        return this.http.get<Page<ProfileSearch>>(ProfileMappings.GET_PROFILES_SEARCH_BY_PATTERN_MAPPING, {
            params: {
                pattern: pattern,
                pageNumber: pageNumber,
                pageSize: pageSize
            }
        }).pipe(take(1));
    }

    updateProfilePicture(profilePictureId: number): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(ProfileMappings.UPDATE_PROFILE_PICTURE_MAPPING, {}, {
            params: {
                profilePictureId: profilePictureId
            }
        });
    }

    updateProfileBanner(profileBannerId: number): Observable<ProfileRequest> {
        return this.http.put<ProfileRequest>(ProfileMappings.UPDATE_PROFILE_BANNER_MAPPING, {}, {
            params: {
                profileBannerId: profileBannerId
            }
        });
    }

    getProfileInfoByUsername(username: string): Observable<Profile> {
        return this.http.get<Profile>(ProfileMappings.GET_PROFILE_INFO_BY_USERNAME_MAPPING, {
            params: {
                username: username
            }
        }).pipe(take(1));
    }
}
