import { Component } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { ProfileComponent } from "@pages/feed/profile/profile.component";
import { PostsFeedComponent } from "@pages/feed/posts-feed/posts-feed.component";
import { Profile } from "@core/data/feed/Profile";

@Component({
    selector: 'app-feed',
    standalone: true,
    imports: [
        ToolbarComponent,
        ProfileComponent,
        PostsFeedComponent
    ],
    templateUrl: './feed.component.html',
    styleUrl: './feed.component.scss'
})
export class FeedComponent {
    protected profile: Profile = {
        fullName: "John Doe",
        jobPosition: "Web developer at Google",
        description: "I am a simple man with big ambitions. " +
            "I love to code and I am passionate about web development. " +
            "I am a team player and I am always looking for new challenges.",
        postCount: 256,
        numberOfFriends: 1025
    };
}
