import { Component, OnInit } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { MatButton, MatMiniFabButton } from "@angular/material/button";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatAutocompleteTrigger } from "@angular/material/autocomplete";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatTooltip } from "@angular/material/tooltip";
import { FriendProfileOption } from "@interfaces/profile/friend-profile-option.interface";
import { FriendProfileComponent } from "@pages/profile/profile-friends/friend-profile/friend-profile.component";
import { RouterLink } from "@angular/router";
import { Page } from "@interfaces/feed/page.interface";
import { FriendsService } from "@api/profile/friends.service";
import { LocalStorageService } from '@core/services/utils/local-storage.service';
import { Profile } from "@interfaces/feed/profile.interface";

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
        RouterLink,
    ],
    templateUrl: './profile-friends.component.html',
    styleUrl: './profile-friends.component.scss'
})
export class ProfileFriendsComponent implements OnInit {

    protected searchSocialControl: FormControl<string | null> = new FormControl<string>("");
    protected friends !: Page<FriendProfileOption>;
    private currentUser !: Profile;
    private pageNumber: number = 0;
    private pageSize: number = 15;

    constructor(private friendsService: FriendsService,
                private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();

        this.friendsService
            .getFriends(this.currentUser.profileId, this.pageNumber, this.pageSize)
            .subscribe((friends) => this.friends = friends);
    }

}
