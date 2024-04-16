import { Component } from '@angular/core';
import { MatCardModule } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { FriendRequestComponent } from "@pages/friends/friend-requests/friend-request/friend-request.component";
import { FriendElement } from "@interfaces/friends/friend-element.interface";

@Component({
    selector: 'app-friend-requests',
    standalone: true,
    imports: [
        MatCardModule,
        MatButton,
        FriendRequestComponent
    ],
    templateUrl: './friend-requests.component.html',
    styleUrl: './friend-requests.component.scss'
})
export class FriendRequestsComponent {
    friendRequests: FriendElement[] = [
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
