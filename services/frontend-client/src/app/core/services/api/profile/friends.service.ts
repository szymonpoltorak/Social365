import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Page } from "@interfaces/utils/page.interface";
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

    getFriends(pageNumber: number, pageSize: number): Observable<Page<FriendProfileOption>> {
        return this.http.get<Page<FriendProfileOption>>(FriendsMappings.GET_FRIENDS_ON_PAGE, {
            params: {
                pageNumber: pageNumber,
                pageSize: pageSize
            }
        }).pipe(take(1));
    }

    getFriendsByPattern(pattern: string, pageNumber: number, pageSize: number): Observable<Page<FriendProfileOption>> {
        return this.http.get<Page<FriendProfileOption>>(FriendsMappings.GET_FRIENDS_BY_PATTERN, {
            params: {
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

    getFriendRequests(pageNumber: number, pageSize: number): Observable<Page<FriendElement>> {
        return this.http.get<Page<FriendElement>>(FriendsMappings.FRIEND_REQUESTS, {
            params: {
                pageNumber: pageNumber,
                pageSize: pageSize
            }
        }).pipe(take(1));
    }

    getFriendSuggestions(pageNumber: number, pageSize: number): Observable<Page<FriendElement>> {
        return this.http.get<Page<FriendElement>>(FriendsMappings.FRIEND_SUGGESTIONS, {
            params: {
                pageNumber: pageNumber,
                pageSize: pageSize
            }
        }).pipe(take(1));
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
