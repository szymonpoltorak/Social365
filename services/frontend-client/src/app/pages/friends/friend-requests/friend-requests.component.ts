import { Component, HostListener, OnInit } from '@angular/core';
import { MatCardModule } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { FriendRequestComponent } from "@pages/friends/friend-requests/friend-request/friend-request.component";
import { FriendElement } from "@interfaces/friends/friend-element.interface";
import { Page } from "@interfaces/utils/page.interface";
import { LocalStorageService } from '@core/services/utils/local-storage.service';
import { FriendsService } from "@api/profile/friends.service";
import { Profile } from "@interfaces/feed/profile.interface";
import { MatProgressSpinner } from "@angular/material/progress-spinner";

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

    protected friendRequests !: Page<FriendElement>;
    protected currentUser !: Profile;
    private readonly FIRST_PAGE: number = 0;
    private readonly PAGE_SIZE: number = 20;

    constructor(private friendsService: FriendsService,
                private localStorageService: LocalStorageService,) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorageService.getUserProfileFromStorage();

        this.friendsService
            .getFriendRequests(this.currentUser.profileId, this.FIRST_PAGE, this.PAGE_SIZE)
            .subscribe((response: Page<FriendElement>) => {
                this.friendRequests = response;
            });
    }

    @HostListener('window:scroll', ['$event'])
    onScroll(event: any): void {
        if (this.friendRequests.last) {
            return;
        }
        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position < max) {
            return;
        }
        this.friendsService
            .getFriendSuggestions(this.currentUser.profileId, this.friendRequests.number + 1, this.PAGE_SIZE)
            .subscribe((response: Page<FriendElement>) => this.friendRequests = response);
    }

}
