import { Component, HostListener, OnInit } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { MatButton, MatMiniFabButton } from "@angular/material/button";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatAutocompleteTrigger } from "@angular/material/autocomplete";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatTooltip } from "@angular/material/tooltip";
import { FriendProfileComponent } from "@pages/profile/profile-friends/friend-profile/friend-profile.component";
import { Router, RouterLink } from "@angular/router";
import { FriendsService } from "@api/profile/friends.service";
import { debounceTime, distinctUntilChanged, startWith, tap } from "rxjs";
import { Optional } from "@core/types/profile/optional.type";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";
import { RouterPaths } from "@enums/router-paths.enum";
import { FriendElement } from "@interfaces/friends/friend-element.interface";

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
        MatProgressSpinner,
    ],
    templateUrl: './profile-friends.component.html',
    styleUrl: './profile-friends.component.scss'
})
export class ProfileFriendsComponent implements OnInit {

    protected searchSocialControl: FormControl<Optional<string>> = new FormControl<string>("");
    protected friends !: SocialPage<FriendElement, PageablePagingState>;
    private readonly PAGE_SIZE: number = 15;

    constructor(private friendsService: FriendsService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.searchSocialControl
            .valueChanges
            .pipe(
                startWith(""),
                tap(value => {
                    this.fetchFriends(value, PageablePagingState.firstPage(this.PAGE_SIZE));
                }),
                distinctUntilChanged(),
                debounceTime(1500),
            )
            .subscribe(value => {
                this.fetchFriends(value, PageablePagingState.firstPage(this.PAGE_SIZE));
            });
    }

    @HostListener('window:scroll', ['$event'])
    onScroll(): void {
        if (!this.friends.hasNextPage) {
            return;
        }
        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position < max) {
            return;
        }
        this.fetchFriends(this.searchSocialControl.value, this.friends.nextPagingState());
    }

    private fetchFriends(value: Optional<string>, pagingState: PageablePagingState): void {
        if (value === "") {
            this.friendsService
                .getFriends(pagingState)
                .subscribe((friends: SocialPage<FriendElement, PageablePagingState>) => this.friends = friends);
        } else if (value !== null) {
            this.friendsService
                .getFriendsByPattern(value as string, pagingState)
                .subscribe((friends: SocialPage<FriendElement, PageablePagingState>) => this.friends = friends);
        }
    }

    navigateToUserProfile(username: string): void {
        this.router.navigate([RouterPaths.PROFILE_DIRECT, username, RouterPaths.PROFILE_POSTS]);
    }
}
