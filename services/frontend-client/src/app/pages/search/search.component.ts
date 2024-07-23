import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { take } from "rxjs";
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { MatCard, MatCardTitle } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { ProfileService } from "@api/profile/profile.service";
import { Page } from "@interfaces/utils/page.interface";
import { ProfileSearch } from "@interfaces/search/profile-search.interface";
import { FriendsService } from "@api/profile/friends.service";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { RoutingService } from "@services/profile/routing.service";

@Component({
    selector: 'app-search',
    standalone: true,
    imports: [
        ToolbarComponent,
        MatCard,
        MatCardTitle,
        MatButton,
        MatProgressSpinner
    ],
    templateUrl: './search.component.html',
    styleUrl: './search.component.scss'
})
export class SearchComponent implements OnInit {

    profiles !: Page<ProfileSearch>;
    private readonly FIRST_PAGE: number = 0;
    private readonly PAGE_SIZE: number = 20;
    private pattern !: string;

    constructor(private activatedRoute: ActivatedRoute,
                private friendsService: FriendsService,
                protected routingService: RoutingService,
                private localStorage: LocalStorageService,
                private profileService: ProfileService) {
    }

    ngOnInit(): void {
        this.activatedRoute.queryParams
            .pipe(take(1))
            .subscribe(params => {
                this.pattern = params['pattern'];

                this.profileService
                    .getProfilesSearchByPattern(params['pattern'], this.FIRST_PAGE, this.PAGE_SIZE)
                    .subscribe((profiles: Page<ProfileSearch>) => {
                        this.profiles = profiles;
                    });
            });
    }

    @HostListener('window:scroll', ['$event'])
    onScroll(event: any): void {
        if (this.profiles.last) {
            return;
        }
        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position < max) {
            return;
        }
        this.profileService
            .getProfilesSearchByPattern(this.pattern, this.profiles.number + 1, this.PAGE_SIZE)
            .subscribe((profiles: Page<ProfileSearch>) => {
                const oldContent: ProfileSearch[] = this.profiles.content;

                this.profiles = profiles;

                this.profiles.content = oldContent.concat(profiles.content);
            });
    }

    addFriend(friendId: string): void {
        this.friendsService
            .sendFriendRequest(this.localStorage.getUserProfileIdFromStorage(), friendId)
            .subscribe(() => {
                this.profiles.content = this.profiles.content
                    .filter((profile: ProfileSearch) => profile.profileId !== friendId);
            });
    }

}
