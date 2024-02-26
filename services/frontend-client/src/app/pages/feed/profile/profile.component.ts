import { Component } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatCard, MatCardContent, MatCardSubtitle, MatCardTitle } from "@angular/material/card";
import { MatDivider } from "@angular/material/divider";
import { MatIcon } from "@angular/material/icon";

@Component({
    selector: 'app-profile',
    standalone: true,
    imports: [
        MatButton,
        MatCard,
        MatCardContent,
        MatCardSubtitle,
        MatCardTitle,
        MatDivider,
        MatIcon
    ],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.scss'
})
export class ProfileComponent {
    protected fullName: string = "John Doe";
    protected jobPosition: string = "Web developer at Google";
    protected description: string = "I am a simple man with big ambitions. " +
        "I love to code and I am passionate about web development. " +
        "I am a team player and I am always looking for new challenges.";
    protected postCount: number = 256;
    protected numberOfFriends: number = 1025;
}
