import { Component } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatCard, MatCardContent, MatCardSubtitle, MatCardTitle } from "@angular/material/card";
import { MatDivider } from "@angular/material/divider";

@Component({
    selector: 'app-feed',
    standalone: true,
    imports: [
        ToolbarComponent,
        MatButton,
        MatIcon,
        MatCard,
        MatCardSubtitle,
        MatCardTitle,
        MatCardContent,
        MatDivider
    ],
    templateUrl: './feed.component.html',
    styleUrl: './feed.component.scss'
})
export class FeedComponent {
    protected fullName: string = "John Doe";
    protected jobPosition: string = "Web developer at Google";
    protected description: string = "I am a simple man with big ambitions. " +
        "I love to code and I am passionate about web development. " +
        "I am a team player and I am always looking for new challenges.";
    protected postCount: number = 256;
    protected numberOfFriends: number = 1025;

}
