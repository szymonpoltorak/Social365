import { Component, Input } from '@angular/core';
import { RouterPaths } from "@enums/router-paths.enum";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { BirthdayInfo } from "@interfaces/feed/birthday-info.interface";
import { Router } from "@angular/router";

@Component({
    selector: 'app-birthday-info',
    standalone: true,
    imports: [
        AvatarPhotoComponent,
        MatButton,
        MatIcon
    ],
    templateUrl: './birthday-info.component.html',
    styleUrl: './birthday-info.component.scss'
})
export class BirthdayInfoComponent {

    @Input() birthdayInfo !: BirthdayInfo;
    protected readonly RouterPaths = RouterPaths;

    constructor(protected router: Router) {
    }

}
