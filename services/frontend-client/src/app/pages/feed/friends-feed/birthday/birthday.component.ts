import { Component, Input } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { BirthdayInfo } from "@interfaces/feed/birthday-info.interface";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { RouterPaths } from "@enums/router-paths.enum";
import { Router } from '@angular/router';

@Component({
    selector: 'app-birthday',
    standalone: true,
    imports: [
        MatButton,
        MatIcon,
        MatDivider,
        AvatarPhotoComponent,
    ],
    templateUrl: './birthday.component.html',
    styleUrl: './birthday.component.scss'
})
export class BirthdayComponent {
    @Input() birthdayInfos !: BirthdayInfo[];
    protected isExpanded: boolean = false;
    protected readonly RouterPaths = RouterPaths;

    constructor(protected router: Router) {
    }
}
