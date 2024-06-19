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
import { LocalStorageService } from "@services/utils/local-storage.service";

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
                private profileService: ProfileService,
                private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        const profileId: string = "d1264dbe-e16f-4b29-8bc5-3d54f6d2b93f";

        this.profileService
            .getProfileSummary(profileId)
            .subscribe((profile: ProfileSummary) => {
                this.profile = profile;

                this.localStorage.saveUserToStorage({
                    profileId: this.profile.profileId,
                    fullName: this.profile.fullName,
                    username: this.profile.username,
                    subtitle: this.profile.subtitle,
                    bio: this.profile.bio,
                    profilePictureUrl: this.profile.profilePictureUrl
                });
            });
    }
}
