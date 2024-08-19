import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { take } from "rxjs";
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { MatCard, MatCardTitle } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { ProfileService } from "@api/profile/profile.service";
import { ProfileSearch } from "@interfaces/search/profile-search.interface";
import { FriendsService } from "@api/profile/friends.service";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { RoutingService } from "@services/profile/routing.service";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";

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

    protected profiles !: SocialPage<ProfileSearch, PageablePagingState>;
    private readonly FIRST_PAGE: number = 0;
    private readonly PAGE_SIZE: number = 20;
    private pattern !: string;

    constructor(private activatedRoute: ActivatedRoute,
                private friendsService: FriendsService,
                protected routingService: RoutingService,
                private profileService: ProfileService) {
    }

    ngOnInit(): void {
        this.activatedRoute.queryParams
            .pipe(take(1))
            .subscribe(params => {
                this.pattern = params['pattern'];

                this.profileService
                    .getProfilesSearchByPattern(params['pattern'], new PageablePagingState(this.FIRST_PAGE, this.PAGE_SIZE))
                    .subscribe((profiles: SocialPage<ProfileSearch, PageablePagingState>) => this.profiles = profiles);
            });
    }

    @HostListener('window:scroll', ['$event'])
    onScroll(): void {
        if (!this.profiles.hasNextPage) {
            return;
        }
        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position < max) {
            return;
        }
        this.profileService
            .getProfilesSearchByPattern(this.pattern, this.profiles.nextPagingState())
            .subscribe((profiles: SocialPage<ProfileSearch, PageablePagingState>) => this.profiles.updatePage(profiles));
    }

    addFriend(friend: ProfileSearch): void {
        this.friendsService
            .sendFriendRequest(friend.profileId)
            .subscribe(() => this.profiles.remove(friend));
    }

}
