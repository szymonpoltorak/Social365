import { Component, HostListener, OnInit } from '@angular/core';
import { FriendElement } from "@interfaces/friends/friend-element.interface";
import { FriendRequestComponent } from "@pages/friends/friend-requests/friend-request/friend-request.component";
import { FriendsService } from "@api/profile/friends.service";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";

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

    protected friendSuggestions !: SocialPage<FriendElement, PageablePagingState>;
    private readonly PAGE_SIZE: number = 20;

    constructor(private friendsService: FriendsService) {
    }

    ngOnInit(): void {
        this.friendsService
            .getFriendSuggestions(PageablePagingState.firstPage(this.PAGE_SIZE))
            .subscribe((response: SocialPage<FriendElement, PageablePagingState>) => this.friendSuggestions = response);
    }

    @HostListener('window:scroll', ['$event'])
    onScroll(): void {
        if (!this.friendSuggestions.hasNextPage) {
            return;
        }
        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position < max) {
            return;
        }
        this.friendsService
            .getFriendSuggestions(this.friendSuggestions.nextPagingState())
            .subscribe((response: SocialPage<FriendElement, PageablePagingState>) => this.friendSuggestions = response);
    }

    sendFriendRequest(event: FriendElement): void {
        this.friendsService
            .sendFriendRequest(event.profileId)
            .subscribe(() => this.friendSuggestions.remove(event));
    }

    removeSuggestion(event: FriendElement): void {
        this.friendSuggestions.remove(event);
    }

}
