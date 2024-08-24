import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, take } from 'rxjs';
import { FriendsMappings } from "@enums/api/profile/friends-mappings.enum";
import { FriendElement } from "@interfaces/friends/friend-element.interface";
import { FriendFeedOption } from "@interfaces/feed/friend-feed-option.interface";
import { FriendProfileOption } from "@interfaces/profile/friend-profile-option.interface";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from '@core/utils/pageable-paging-state';

@Injectable({
    providedIn: 'root'
})
export class FriendsService {

    constructor(private http: HttpClient) {
    }

    getFriends(pagingState: PageablePagingState): Observable<SocialPage<FriendProfileOption, PageablePagingState>> {
        return this.http.get<SocialPage<FriendProfileOption, PageablePagingState>>(FriendsMappings.GET_FRIENDS_ON_PAGE, {
            params: {
                pageNumber: pagingState.pageNumber,
                pageSize: pagingState.pageSize
            }
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<FriendProfileOption, PageablePagingState>(json))
        );
    }

    getFriendsByPattern(pattern: string, pagingState: PageablePagingState): Observable<SocialPage<FriendProfileOption, PageablePagingState>> {
        return this.http.get<SocialPage<FriendProfileOption, PageablePagingState>>(FriendsMappings.GET_FRIENDS_BY_PATTERN, {
            params: {
                pattern: pattern,
                pageNumber: pagingState.pageNumber,
                pageSize: pagingState.pageSize
            }
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<FriendProfileOption, PageablePagingState>(json))
        );
    }

    getFriendsFeedOptions(profileId: string, pagingState: PageablePagingState): Observable<SocialPage<FriendFeedOption, PageablePagingState>> {
        return this.http.get<SocialPage<FriendFeedOption, PageablePagingState>>(FriendsMappings.GET_FRIENDS_FEED_OPTIONS, {
            params: {
                profileId: profileId,
                pageNumber: pagingState.pageNumber,
                pageSize: pagingState.pageSize
            }
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<FriendFeedOption, PageablePagingState>(json))
        );
    }

    getFriendRequests(pagingState: PageablePagingState): Observable<SocialPage<FriendElement, PageablePagingState>> {
        return this.http.get<SocialPage<FriendElement, PageablePagingState>>(FriendsMappings.FRIEND_REQUESTS, {
            params: {
                pageNumber: pagingState.pageNumber,
                pageSize: pagingState.pageSize
            }
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<FriendElement, PageablePagingState>(json))
        );
    }

    getFriendSuggestions(pagingState: PageablePagingState): Observable<SocialPage<FriendElement, PageablePagingState>> {
        return this.http.get<SocialPage<FriendElement, PageablePagingState>>(FriendsMappings.FRIEND_SUGGESTIONS, {
            params: {
                pageNumber: pagingState.pageNumber,
                pageSize: pagingState.pageSize
            }
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<FriendElement, PageablePagingState>(json))
        );
    }

    sendFriendRequest(friendId: string): Observable<FriendElement> {
        return this.http.post<FriendElement>(FriendsMappings.SEND_FRIEND_REQUEST, {
            friendId: friendId
        }).pipe(take(1));
    }

    removeUserFromFriends(friendId: string): Observable<FriendElement> {
        return this.http.delete<FriendElement>(FriendsMappings.REMOVE_FRIEND, {
            params: {
                friendId: friendId
            }
        }).pipe(take(1));
    }

    addUserToFriends(friendId: string): Observable<FriendElement> {
        return this.http.put<FriendElement>(FriendsMappings.ADD_FRIEND, {
            friendId: friendId
        }).pipe(take(1));
    }

    addProfileToFollowed(friendId: string): Observable<FriendElement> {
        return this.http.put<FriendElement>(FriendsMappings.ADD_PROFILE_TO_FOLLOWERS, {
            friendId: friendId
        }).pipe(take(1));
    }

    removeProfileFromFollowed(friendId: string): Observable<FriendElement> {
        return this.http.delete<FriendElement>(FriendsMappings.REMOVE_PROFILE_FROM_FOLLOWERS, {
            params: {
                friendId: friendId
            }
        }).pipe(take(1));
    }

    acceptFriendRequest(friendId: string): Observable<FriendElement> {
        return this.http.put<FriendElement>(FriendsMappings.ACCEPT_FRIEND_REQUEST, {
            friendId: friendId
        }).pipe(take(1));
    }

    declineFriendRequest(friendId: string): Observable<FriendElement> {
        return this.http.put<FriendElement>(FriendsMappings.DECLINE_FRIEND_REQUEST, {
            friendId: friendId
        }).pipe(take(1));
    }
}
