import { Component } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";

@Component({
    selector: 'app-profile-photos',
    standalone: true,
    imports: [
        MatCard,
        MatCardHeader,
        MatCardTitle,
        MatCardContent,
        MatIconButton,
        MatIcon,
        MatMenu,
        MatMenuTrigger,
        MatMenuItem
    ],
    templateUrl: './profile-photos.component.html',
    styleUrl: './profile-photos.component.scss'
})
export class ProfilePhotosComponent {
    protected photos: string[] = ["1", "2", "3", "4", "5", "6", "7"];
}
