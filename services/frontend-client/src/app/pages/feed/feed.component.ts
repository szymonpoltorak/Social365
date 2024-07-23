import { Component, OnInit } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { ProfileFeedComponent } from "@pages/feed/profile-feed/profile-feed.component";
import { PostsFeedComponent } from "@pages/feed/posts-feed/posts-feed.component";
import { FriendsFeedComponent } from "@pages/feed/friends-feed/friends-feed.component";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { Profile } from "@interfaces/feed/profile.interface";
import { PostMappings } from "@enums/api/posts-comments/post-mappings.enum";

@Component({
    selector: 'app-feed',
    standalone: true,
    imports: [
        ToolbarComponent,
        ProfileFeedComponent,
        PostsFeedComponent,
        FriendsFeedComponent
    ],
    templateUrl: './feed.component.html',
    styleUrl: './feed.component.scss'
})
export class FeedComponent implements OnInit {

    protected currentUser !: Profile;
    protected readonly PostMappings = PostMappings;

    constructor(private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();
    }
}
