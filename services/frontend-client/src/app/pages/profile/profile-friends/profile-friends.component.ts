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
import { FriendsService } from "@api/profile/friends.service";
import { debounceTime, distinctUntilChanged, startWith, tap } from "rxjs";
import { Optional } from "@core/types/profile/optional.type";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";

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
    protected friends !: SocialPage<FriendProfileOption, PageablePagingState>;
    private readonly FIRST_PAGE: number = 0;
    private readonly PAGE_SIZE: number = 15;

    constructor(private friendsService: FriendsService) {
    }

    ngOnInit(): void {
        this.searchSocialControl
            .valueChanges
            .pipe(
                startWith(""),
                tap(value => {
                    this.fetchFriends(value, new PageablePagingState(this.PAGE_SIZE, this.FIRST_PAGE));
                }),
                distinctUntilChanged(),
                debounceTime(1500),
            )
            .subscribe(value => {
                this.fetchFriends(value, new PageablePagingState(this.PAGE_SIZE, this.FIRST_PAGE));
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
                .subscribe((friends: SocialPage<FriendProfileOption, PageablePagingState>) => {
                    this.friends = friends;
                });
        } else if (value !== null) {
            this.friendsService
                .getFriendsByPattern(value as string, pagingState)
                .subscribe((friends: SocialPage<FriendProfileOption, PageablePagingState>) => {
                    this.friends = friends;
                });
        }
    }

}
