import { Component, HostListener, OnInit } from '@angular/core';
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
import { debounceTime, distinctUntilChanged, startWith, tap } from "rxjs";
import { Optional } from "@core/types/profile/optional.type";

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

    protected searchSocialControl: FormControl<Optional<string>> = new FormControl<string>("");
    protected friends !: Page<FriendProfileOption>;
    private currentUser !: Profile;
    private readonly FIRST_PAGE: number = 0;
    private pageSize: number = 15;

    constructor(private friendsService: FriendsService,
                private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();

        this.searchSocialControl
            .valueChanges
            .pipe(
                startWith(""),
                tap(value => {
                    this.fetchFriends(value, this.FIRST_PAGE);
                }),
                distinctUntilChanged(),
                debounceTime(1500),
            )
            .subscribe(value => {
                this.fetchFriends(value, this.FIRST_PAGE);
            });
    }

    @HostListener('window:scroll', ['$event'])
    onScroll(event: any): void {
        if (this.friends.last) {
            return;
        }
        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position < max) {
            return;
        }
        this.fetchFriends(this.searchSocialControl.value, this.friends.number + 1);
    }

    private fetchFriends(value: Optional<string>, pageNumber: number): void {
        if (value === "") {
            this.friendsService
                .getFriends(this.currentUser.profileId, pageNumber, this.pageSize)
                .subscribe((friends: Page<FriendProfileOption>) => {
                    this.friends = friends;
                });
        } else if (value !== null) {
            this.friendsService
                .getFriendsByPattern(this.currentUser.profileId, value as string, pageNumber, this.pageSize)
                .subscribe((friends: Page<FriendProfileOption>) => {
                    this.friends = friends;
                });
        }
    }

}
