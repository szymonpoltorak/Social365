import { Component, HostListener, OnInit } from '@angular/core';
import { MatCardModule } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { FriendRequestComponent } from "@pages/friends/friend-requests/friend-request/friend-request.component";
import { FriendElement } from "@interfaces/friends/friend-element.interface";
import { FriendsService } from "@api/profile/friends.service";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";

@Component({
    selector: 'app-friend-requests',
    standalone: true,
    imports: [
        MatCardModule,
        MatButton,
        FriendRequestComponent,
        MatProgressSpinner
    ],
    templateUrl: './friend-requests.component.html',
    styleUrl: './friend-requests.component.scss'
})
export class FriendRequestsComponent implements OnInit {

    protected friendRequests !: SocialPage<FriendElement, PageablePagingState>;
    private readonly FIRST_PAGE: number = 0;
    private readonly PAGE_SIZE: number = 20;

    constructor(private friendsService: FriendsService) {
    }

    ngOnInit(): void {
        this.friendsService
            .getFriendRequests(new PageablePagingState(this.FIRST_PAGE, this.PAGE_SIZE))
            .subscribe((response: SocialPage<FriendElement, PageablePagingState>) => {
                this.friendRequests = response;
            });
    }

    @HostListener('window:scroll', ['$event'])
    onScroll(): void {
        if (!this.friendRequests.hasNextPage) {
            return;
        }
        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position < max) {
            return;
        }
        this.friendsService
            .getFriendSuggestions(this.friendRequests.nextPagingState())
            .subscribe((response: SocialPage<FriendElement, PageablePagingState>) => this.friendRequests = response);
    }

    acceptFriendRequest(event: FriendElement): void {
        this.friendsService
            .acceptFriendRequest(event.profileId)
            .subscribe(() => this.friendRequests.remove(event));
    }

    declineFriendRequest(event: FriendElement): void {
        this.friendsService
            .declineFriendRequest(event.profileId)
            .subscribe(() => this.friendRequests.remove(event));
    }

}
