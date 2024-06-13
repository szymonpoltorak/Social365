import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { ImageDialogData } from "@interfaces/feed/image-dialog-data.interface";
import { MatIcon } from "@angular/material/icon";
import { MatIconButton } from "@angular/material/button";

@Component({
    selector: 'app-image-dialog',
    standalone: true,
    imports: [
        MatIcon,
        MatIconButton
    ],
    templateUrl: './image-dialog.component.html',
    styleUrl: './image-dialog.component.scss'
})
export class ImageDialogComponent {
    constructor(@Inject(MAT_DIALOG_DATA) protected data: ImageDialogData) {
    }

    protected readonly console = console;
}
