import { Component } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { ProfileComponent } from "@pages/feed/profile/profile.component";
import { PostsFeedComponent } from "@pages/feed/posts-feed/posts-feed.component";

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

}
