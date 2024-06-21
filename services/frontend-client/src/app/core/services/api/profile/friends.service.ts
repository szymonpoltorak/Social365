import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Page } from "@interfaces/feed/page.interface";
import { Observable, take } from 'rxjs';
import { FriendsMappings } from "@enums/api/profile/friends-mappings.enum";
import { FriendElement } from "@interfaces/friends/friend-element.interface";
import { FriendFeedOption } from "@interfaces/feed/friend-feed-option.interface";
import { FriendProfileOption } from "@interfaces/profile/friend-profile-option.interface";

@Injectable({
    providedIn: 'root'
})
export class FriendsService {

    constructor(private http: HttpClient) {
    }

    getFriends(profileId: string, pageNumber: number, pageSize: number): Observable<Page<FriendProfileOption>> {
        return this.http.get<Page<FriendProfileOption>>(FriendsMappings.GET_FRIENDS_ON_PAGE, {
            params: {
                profileId: profileId,
                pageNumber: pageNumber,
                pageSize: pageSize
            }
        }).pipe(take(1));
    }

    getFriendsByPattern(profileId: string, pattern: string,
                        pageNumber: number, pageSize: number): Observable<Page<FriendProfileOption>> {
        return this.http.get<Page<FriendProfileOption>>(FriendsMappings.GET_FRIENDS_BY_PATTERN, {
            params: {
                profileId: profileId,
                pattern: pattern,
                pageNumber: pageNumber,
                pageSize: pageSize
            }
        }).pipe(take(1));
    }

    getFriendsFeedOptions(profileId: string, pageNumber: number, pageSize: number): Observable<Page<FriendFeedOption>> {
        return this.http.get<Page<FriendFeedOption>>(FriendsMappings.GET_FRIENDS_FEED_OPTIONS, {
            params: {
                profileId: profileId,
                pageNumber: pageNumber,
                pageSize: pageSize
            }
        }).pipe(take(1));
    }

    getFriendRequests(profileId: string, pageNumber: number, pageSize: number): Observable<Page<FriendElement>> {
        return this.http.get<Page<FriendElement>>(FriendsMappings.FRIEND_REQUESTS, {
            params: {
                profileId: profileId,
                pageNumber: pageNumber,
                pageSize: pageSize
            }
        }).pipe(take(1));
    }

    getFriendSuggestions(profileId: string, pageNumber: number, pageSize: number): Observable<Page<FriendElement>> {
        return this.http.get<Page<FriendElement>>(FriendsMappings.FRIEND_SUGGESTIONS, {
            params: {
                profileId: profileId,
                pageNumber: pageNumber,
                pageSize: pageSize
            }
        }).pipe(take(1));
    }

    sendFriendRequest(profileId: string, friendId: string): Observable<FriendElement> {
        return this.http.post<FriendElement>(FriendsMappings.SEND_FRIEND_REQUEST, {
            profileId: profileId,
            friendId: friendId
        }).pipe(take(1));
    }

    removeUserFromFriends(profileId: string, friendId: string): Observable<FriendElement> {
        return this.http.delete<FriendElement>(FriendsMappings.REMOVE_FRIEND, {
            params: {
                profileId: profileId,
                friendId: friendId
            }
        }).pipe(take(1));
    }

    addUserToFriends(profileId: string, friendId: string): Observable<FriendElement> {
        return this.http.put<FriendElement>(FriendsMappings.ADD_FRIEND, {
            profileId: profileId,
            friendId: friendId
        }).pipe(take(1));
    }

    addProfileToFollowed(profileId: string, friendId: string): Observable<FriendElement> {
        return this.http.put<FriendElement>(FriendsMappings.ADD_PROFILE_TO_FOLLOWERS, {
            profileId: profileId,
            friendId: friendId
        }).pipe(take(1));
    }

    removeProfileFromFollowed(profileId: string, friendId: string): Observable<FriendElement> {
        return this.http.delete<FriendElement>(FriendsMappings.REMOVE_PROFILE_FROM_FOLLOWERS, {
            params: {
                profileId: profileId,
                friendId: friendId
            }
        }).pipe(take(1));
    }

    acceptFriendRequest(profileId: string, friendId: string): Observable<FriendElement> {
        return this.http.put<FriendElement>(FriendsMappings.ACCEPT_FRIEND_REQUEST, {
            profileId: profileId,
            friendId: friendId
        }).pipe(take(1));
    }

    declineFriendRequest(profileId: string, friendId: string): Observable<FriendElement> {
        return this.http.put<FriendElement>(FriendsMappings.DECLINE_FRIEND_REQUEST, {
            profileId: profileId,
            friendId: friendId
        }).pipe(take(1));
    }
}
