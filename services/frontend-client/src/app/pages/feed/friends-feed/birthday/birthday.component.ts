import { Component, Input } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { BirthdayInfo } from "@interfaces/feed/birthday-info.interface";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { RouterPaths } from "@enums/router-paths.enum";
import { Router } from '@angular/router';
import { Page } from "@interfaces/utils/page.interface";
import { MatDialog } from "@angular/material/dialog";
import {
    BirthdayInfoDialogComponent
} from "@pages/feed/friends-feed/birthday/birthday-info-dialog/birthday-info-dialog.component";
import { BirthdayInfoComponent } from "@pages/feed/friends-feed/birthday/birthday-info/birthday-info.component";

@Component({
    selector: 'app-birthday',
    standalone: true,
    imports: [
        MatButton,
        MatIcon,
        MatDivider,
        AvatarPhotoComponent,
        BirthdayInfoComponent,
    ],
    templateUrl: './birthday.component.html',
    styleUrl: './birthday.component.scss'
})
export class BirthdayComponent {
    @Input() birthdayInfos !: Page<BirthdayInfo>;
    protected isExpanded: boolean = false;
    protected readonly RouterPaths = RouterPaths;

    constructor(protected router: Router,
                private dialog: MatDialog) {
    }

    showInfosDialog(): void {
        this.dialog.open(BirthdayInfoDialogComponent, {
            data: this.birthdayInfos,
            exitAnimationDuration: 100,
        });
    }
}
