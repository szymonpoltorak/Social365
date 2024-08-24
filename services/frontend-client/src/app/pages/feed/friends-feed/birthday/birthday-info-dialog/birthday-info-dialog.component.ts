import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogClose } from '@angular/material/dialog';
import { BirthdayInfo } from "@interfaces/feed/birthday-info.interface";
import { ProfileService } from "@api/profile/profile.service";
import { MatButton } from "@angular/material/button";
import { RouterPaths } from "@enums/router-paths.enum";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { BirthdayInfoComponent } from "@pages/feed/friends-feed/birthday/birthday-info/birthday-info.component";
import { MatIcon } from "@angular/material/icon";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";

@Component({
    selector: 'app-birthday-info-dialog',
    standalone: true,
    imports: [
        MatButton,
        MatDialogClose,
        AvatarPhotoComponent,
        BirthdayInfoComponent,
        MatIcon
    ],
    templateUrl: './birthday-info-dialog.component.html',
    styleUrl: './birthday-info-dialog.component.scss'
})
export class BirthdayInfoDialogComponent implements OnInit {

    protected readonly RouterPaths = RouterPaths;

    constructor(@Inject(MAT_DIALOG_DATA) protected page: SocialPage<BirthdayInfo, PageablePagingState>,
                private profileService: ProfileService) {
    }

    ngOnInit(): void {
        this.profileService
            .getTodayBirthdays(this.page.nextPagingState())
            .subscribe(data => this.page.updatePage(data));
    }

    showMoreBirthdays(): void {
        this.profileService
            .getTodayBirthdays(this.page.nextPagingState())
            .subscribe(data => this.page.updatePage(data));
    }

}
