import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogClose } from '@angular/material/dialog';
import { Page } from '@interfaces/utils/page.interface';
import { BirthdayInfo } from "@interfaces/feed/birthday-info.interface";
import { ProfileService } from "@api/profile/profile.service";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { MatButton } from "@angular/material/button";
import { RouterPaths } from "@enums/router-paths.enum";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { BirthdayInfoComponent } from "@pages/feed/friends-feed/birthday/birthday-info/birthday-info.component";
import { MatIcon } from "@angular/material/icon";

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

    constructor(@Inject(MAT_DIALOG_DATA) protected page: Page<BirthdayInfo>,
                private profileService: ProfileService,
                private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        this.profileService
            .getTodayBirthdays(this.page.number + 1)
            .subscribe(data => {
                this.appendData(data);
            })
    }

    showMoreBirthdays(): void {
        this.profileService
            .getTodayBirthdays(this.page.number + 1)
            .subscribe(data => {
                this.appendData(data);
            });
    }

    private appendData(data: Page<BirthdayInfo>) {
        const content: BirthdayInfo[] = [...this.page.content, ...data.content];

        this.page = data;

        data.content = content;
    }
}
