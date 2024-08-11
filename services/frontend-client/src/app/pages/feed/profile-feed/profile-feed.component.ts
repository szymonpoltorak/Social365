import { Component, OnInit } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatCard, MatCardContent, MatCardSubtitle, MatCardTitle } from "@angular/material/card";
import { MatDivider } from "@angular/material/divider";
import { MatIcon } from "@angular/material/icon";
import { ProfileSummary } from "@interfaces/feed/profile-summary.interface";
import { NgOptimizedImage } from "@angular/common";
import { Router, RouterLink } from "@angular/router";
import { RouterPaths } from "@enums/router-paths.enum";
import { ProfileService } from "@api/profile/profile.service";

@Component({
    selector: 'app-profile-feed',
    standalone: true,
    imports: [
        MatButton,
        MatCard,
        MatCardContent,
        MatCardSubtitle,
        MatCardTitle,
        MatDivider,
        MatIcon,
        NgOptimizedImage,
        RouterLink
    ],
    templateUrl: './profile-feed.component.html',
    styleUrl: './profile-feed.component.scss'
})
export class ProfileFeedComponent implements OnInit {

    protected profile !: ProfileSummary;
    protected readonly RouterPaths = RouterPaths;

    constructor(protected router: Router,
                private profileService: ProfileService) {
    }

    ngOnInit(): void {
        this.profileService
            .getProfileSummary()
            .subscribe((profile: ProfileSummary) => this.profile = profile);
    }
}
