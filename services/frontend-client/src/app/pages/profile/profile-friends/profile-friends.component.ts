import { Component } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { MatButton, MatMiniFabButton } from "@angular/material/button";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatAutocompleteTrigger } from "@angular/material/autocomplete";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatTooltip } from "@angular/material/tooltip";
import { FriendProfileOption } from "@interfaces/profile/friend-profile-option.interface";
import { FriendProfileComponent } from "@pages/profile/profile-friends/friend-profile/friend-profile.component";

@Component({
    selector: 'app-profile-friends',
    standalone: true,
    imports: [
        MatCard,
        MatCardHeader,
        MatCardTitle,
        MatCardContent,
        MatButton,
        FormsModule,
        MatAutocompleteTrigger,
        MatIcon,
        MatInput,
        MatMiniFabButton,
        MatTooltip,
        ReactiveFormsModule,
        FriendProfileComponent,
    ],
    templateUrl: './profile-friends.component.html',
    styleUrl: './profile-friends.component.scss'
})
export class ProfileFriendsComponent {
    protected searchSocialControl: FormControl<string | null> = new FormControl<string>("");
    protected friends: FriendProfileOption[] = [
        {
            username: "johndoe",
            fullName: "John Doe",
            mutualFriends: 5,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            username: "janedoe",
            fullName: "Jane Doe",
            mutualFriends: 3,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            username: "johndoe",
            fullName: "John Doe",
            mutualFriends: 5,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            username: "janedoe",
            fullName: "Jane Doe",
            mutualFriends: 3,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            username: "johndoe",
            fullName: "John Doe",
            mutualFriends: 5,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            username: "janedoe",
            fullName: "Jane Doe",
            mutualFriends: 3,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            username: "johndoe",
            fullName: "John Doe",
            mutualFriends: 5,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            username: "janedoe",
            fullName: "Jane Doe",
            mutualFriends: 3,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        }
    ];
}
