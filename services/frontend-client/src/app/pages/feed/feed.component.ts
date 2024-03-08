import { Component } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { ProfileFeedComponent } from "@pages/feed/profile-feed/profile-feed.component";
import { PostsFeedComponent } from "@pages/feed/posts-feed/posts-feed.component";
import { Profile } from "@core/data/feed/Profile";
import { FriendsFeedComponent } from "@pages/feed/friends-feed/friends-feed.component";

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
export class FeedComponent {
    protected profile: Profile = {
        id: "1",
        fullName: "John Doe",
        username: "john@gmail.com",
        subtitle: "Web developer at Google",
        description: "I am a simple man with big ambitions. " +
            "I love to code and I am passionate about web development. " +
            "I am a team player and I am always looking for new challenges.",
        postCount: 256,
        numberOfFriends: 1025,
        numberOfFollowers: 300,
        profilePhotoLink: "https://material.angular.io/assets/img/examples/shiba2.jpg"
    };
}
