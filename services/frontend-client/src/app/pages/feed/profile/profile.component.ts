import { Component, Input } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatCard, MatCardContent, MatCardSubtitle, MatCardTitle } from "@angular/material/card";
import { MatDivider } from "@angular/material/divider";
import { MatIcon } from "@angular/material/icon";
import { Profile } from "@core/data/feed/Profile";

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
    @Input() profile !: Profile;
}
