import { Component, HostListener, OnInit } from '@angular/core';
import { FriendElement } from "@interfaces/friends/friend-element.interface";
import { FriendRequestComponent } from "@pages/friends/friend-requests/friend-request/friend-request.component";
import { Page } from "@interfaces/utils/page.interface";
import { Profile } from "@interfaces/feed/profile.interface";
import { FriendsService } from "@api/profile/friends.service";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { MatProgressSpinner } from "@angular/material/progress-spinner";

@Component({
    selector: 'app-friend-suggestions',
    standalone: true,
    imports: [
        FriendRequestComponent,
        MatProgressSpinner
    ],
    templateUrl: './friend-suggestions.component.html',
    styleUrl: './friend-suggestions.component.scss'
})
export class FriendSuggestionsComponent implements OnInit {

    protected friendSuggestions !: Page<FriendElement>;
    protected currentUser !: Profile;
    private readonly FIRST_PAGE: number = 0;
    private readonly PAGE_SIZE: number = 20;

    constructor(private friendsService: FriendsService,
                private localStorageService: LocalStorageService,) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorageService.getUserProfileFromStorage();

        this.friendsService
            .getFriendSuggestions(this.FIRST_PAGE, this.PAGE_SIZE)
            .subscribe((response: Page<FriendElement>) => this.friendSuggestions = response);
    }

    @HostListener('window:scroll', ['$event'])
    onScroll(): void {
        if (this.friendSuggestions.last) {
            return;
        }
        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position < max) {
            return;
        }
        this.friendsService
            .getFriendSuggestions(this.friendSuggestions.number + 1, this.PAGE_SIZE)
            .subscribe((response: Page<FriendElement>) => this.friendSuggestions = response);
    }

}
