import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { map, Observable, take } from "rxjs";
import { ProfileSummary } from "@interfaces/feed/profile-summary.interface";
import { ProfileMappings } from "@enums/api/profile/profile-mappings.enum";
import { Profile } from "@interfaces/feed/profile.interface";
import { BirthdayInfo } from "@interfaces/feed/birthday-info.interface";
import { ProfileRequest } from "@interfaces/profile/profile-request.interface";
import { ProfileQuery } from "@interfaces/feed/profile-query.interface";
import { ProfileSearch } from "@interfaces/search/profile-search.interface";
import { ProfileBasicInfo } from "@interfaces/profile/profile-basic-info.interface";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    constructor(private http: HttpClient) {
    }

    getTodayBirthdays(pagingState: PageablePagingState): Observable<SocialPage<BirthdayInfo, PageablePagingState>> {
        return this.http.get<SocialPage<BirthdayInfo, PageablePagingState>>(ProfileMappings.GET_TODAY_BIRTHDAYS_MAPPING, {
            params: {
                pageNumber: pagingState.pageNumber
            }
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<BirthdayInfo, PageablePagingState>(json))
        );
    }

    getProfilesByPattern(pattern: string, pagingState: PageablePagingState): Observable<SocialPage<ProfileQuery, PageablePagingState>> {
        return this.http.get<SocialPage<ProfileQuery, PageablePagingState>>(ProfileMappings.GET_PROFILES_BY_PATTERN_MAPPING, {
            params: {
                pattern: pattern,
                pageNumber: pagingState.pageNumber,
                pageSize: pagingState.pageSize
            }
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<ProfileQuery, PageablePagingState>(json))
        );
    }

    getProfileSummary(): Observable<ProfileSummary> {
        return this.http.get<ProfileSummary>(ProfileMappings.GET_PROFILE_SUMMARY_MAPPING).pipe(take(1));
    }

    getBasicProfileInfo(): Observable<Profile> {
        return this.http.get<Profile>(ProfileMappings.GET_BASIC_PROFILE_INFO_MAPPING).pipe(take(1));
    }

    updateProfileBio(bio: string): Observable<ProfileRequest> {
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

    getProfilesSearchByPattern(pattern: string,
                               pagingState: PageablePagingState): Observable<SocialPage<ProfileSearch, PageablePagingState>> {
        return this.http.get<SocialPage<ProfileSearch, PageablePagingState>>(ProfileMappings.GET_PROFILES_SEARCH_BY_PATTERN_MAPPING, {
            params: {
                pattern: pattern,
                pageNumber: pagingState.pageNumber,
                pageSize: pagingState.pageSize
            }
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<ProfileSearch, PageablePagingState>(json))
        );
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
