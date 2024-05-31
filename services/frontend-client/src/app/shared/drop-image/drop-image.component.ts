import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { MatSnackBar } from "@angular/material/snack-bar";
import { MatCard } from "@angular/material/card";
import { MatFabButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";

@Component({
    selector: 'app-drop-image',
    standalone: true,
    imports: [
        MatCard,
        MatFabButton,
        MatIcon,
        MatIconButton
    ],
    templateUrl: './drop-image.component.html',
    styleUrl: './drop-image.component.scss'
})
export class DropImageComponent {

    @ViewChild('fileInput', { static: false }) fileInput!: ElementRef;
    @Input() isAttachingImagesOpened: boolean = false;
    @Output() attachedImagesLength: EventEmitter<number> = new EventEmitter<number>();
    allowedFileTypes: string[] = [
        'image/jpeg',
        'image/png',
    ];
    isUploading = false;
    attachedImages: AttachImage[] = [];

    constructor(private snackBar: MatSnackBar) {
    }

    handleChange(event: any): void {
        const files: File[] = event.target.files as File[];

        for (const file of files) {
            if (this.allowedFileTypes.indexOf(file.type) === -1) {
                this.snackBar.open(`Invalid file type for file named ${ file.name }`, 'Close');

                continue;
            }
            this.attachedImages.push({
                fileUrl: URL.createObjectURL(file),
                file: file,
            });
        }

        if (this.attachedImages.length > 0) {
            this.snackBar.open(`Successfully attached ${ this.attachedImages.length } images!`, 'Close');
        }
        this.isAttachingImagesOpened = false;
        this.attachedImagesLength.emit(this.attachedImages.length);
    }

    handleRemovesFile(fileToRemove: AttachImage): void {
        if (this.fileInput && this.fileInput.nativeElement) {
            this.fileInput.nativeElement.value = null;
        }
        this.attachedImages = this.attachedImages.filter((file) => file !== fileToRemove);
        this.attachedImagesLength.emit(this.attachedImages.length);
    }
}
