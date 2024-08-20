import { Component, OnInit } from '@angular/core';
import { MatCard } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { FriendFeedOptionComponent } from "@pages/feed/friends-feed/friend/friend-feed-option.component";
import { FriendFeedOption } from "@interfaces/feed/friend-feed-option.interface";
import { Observable, of } from "rxjs";
import { AsyncPipe } from "@angular/common";
import { BirthdayComponent } from "@pages/feed/friends-feed/birthday/birthday.component";
import { BirthdayInfo } from "@interfaces/feed/birthday-info.interface";
import { ProfileService } from "@api/profile/profile.service";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { FriendsService } from '@core/services/api/profile/friends.service';
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";
import { PostsPagingState } from "@core/utils/posts-paging-state";

@Component({
    selector: 'app-friends-feed',
    standalone: true,
    imports: [
        MatCard,
        MatButton,
        MatIcon,
        MatDivider,
        FriendFeedOptionComponent,
        AsyncPipe,
        BirthdayComponent,
        MatProgressSpinner
    ],
    templateUrl: './friends-feed.component.html',
    styleUrl: './friends-feed.component.scss'
})
export class FriendsFeedComponent implements OnInit {

    protected birthdayInfos !: SocialPage<BirthdayInfo, PageablePagingState>;
    protected friends !: Observable<SocialPage<FriendFeedOption, PageablePagingState>>;
    protected groupChats: Observable<FriendFeedOption[]> = of([
        {
            profileId: "1",
            fullName: "My Friends",
            isOnline: true,
            username: "my-friends@example.com",
            profilePictureUrl: "https://static.scientificamerican.com/sciam/cache/file/8F2611FB-1329-445F-9428B91317BC067B_source.jpg?w=1200"
        },
        {
            profileId: "2",
            fullName: "Family",
            isOnline: false,
            username: "my-friends@example.com",
            profilePictureUrl: "https://static.scientificamerican.com/sciam/cache/file/8F2611FB-1329-445F-9428B91317BC067B_source.jpg?w=1200"
        },
        {
            profileId: "3",
            fullName: "Students of WUT",
            isOnline: true,
            username: "my-friends@example.com",
            profilePictureUrl: "https://static.scientificamerican.com/sciam/cache/file/8F2611FB-1329-445F-9428B91317BC067B_source.jpg?w=1200"
        }
    ]);
    private readonly PAGE_SIZE: number = 5;

    constructor(private profileService: ProfileService,
                private friendsService: FriendsService,
                private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        const profileId: string = this.localStorage.getUserProfileIdFromStorage();
        const pagingState: PageablePagingState = PageablePagingState.firstPage(this.PAGE_SIZE);

        this.profileService
            .getTodayBirthdays(pagingState)
            .subscribe(data => this.birthdayInfos = data);

        this.friends = this.friendsService.getFriendsFeedOptions(profileId, pagingState);
    }

}
