import { Component } from '@angular/core';
import { FriendElement } from "@interfaces/friends/friend-element.interface";
import { FriendRequestComponent } from "@pages/friends/friend-requests/friend-request/friend-request.component";

@Component({
    selector: 'app-friend-suggestions',
    standalone: true,
    imports: [
        FriendRequestComponent
    ],
    templateUrl: './friend-suggestions.component.html',
    styleUrl: './friend-suggestions.component.scss'
})
export class FriendSuggestionsComponent {
    friendSuggestions: FriendElement[] = [
        {
            fullName: 'John Doe',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 12,
            profileId: "1"
        },
        {
            fullName: 'Jane Doe',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 3,
            profileId: "2"
        },
        {
            fullName: 'John Smith',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 0,
            profileId: "3"
        },
        {
            fullName: 'John Doe',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 12,
            profileId: "1"
        },
        {
            fullName: 'Jane Doe',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 3,
            profileId: "2"
        },
        {
            fullName: 'John Smith',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 0,
            profileId: "3"
        },
        {
            fullName: 'John Doe',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 12,
            profileId: "1"
        },
        {
            fullName: 'Jane Doe',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 3,
            profileId: "2"
        },
        {
            fullName: 'John Smith',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 0,
            profileId: "3"
        },
        {
            fullName: 'John Doe',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 12,
            profileId: "1"
        },
        {
            fullName: 'Jane Doe',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 3,
            profileId: "2"
        },
        {
            fullName: 'John Smith',
            profilePictureUrl: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
            numOfMutualFriends: 0,
            profileId: "3"
        }
    ];

}
