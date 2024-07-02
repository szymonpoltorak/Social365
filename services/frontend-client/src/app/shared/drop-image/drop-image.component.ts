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
    private readonly MAX_FILE_SIZE: number = 10485760;
    protected allowedFileTypes: string[] = [
        'image/jpeg',
        'image/png',
    ];
    protected attachedImages: AttachImage[] = [];

    constructor(private snackBar: MatSnackBar) {
    }

    onFormSubmit(): AttachImage[] {
        const images: AttachImage[] = this.attachedImages;

        this.attachedImages = [];

        return images;
    }

    handleChange(event: any): void {
        const files: File[] = event.target.files as File[];

        if (files.length > 10) {
            this.snackBar.open('You can only attach up to 10 images!', 'Close', {
                duration: 2000,
            });

            return;
        }

        for (const file of files) {
            if (this.shouldContinue(file)) {
                continue;
            }
            this.attachedImages.push({
                fileUrl: URL.createObjectURL(file),
                file: file,
            });
        }

        if (this.attachedImages.length > 0) {
            this.snackBar.open(`Successfully attached ${ this.attachedImages.length } images!`, 'Close', {
                duration: 2000,
            });
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

    private shouldContinue(file: File): boolean {
        if (this.allowedFileTypes.indexOf(file.type) === -1) {
            this.snackBar.open(`Invalid file type for file named ${ file.name }`, 'Close', {
                duration: 2000,
            });

            return false;
        }
        if (file.size > this.MAX_FILE_SIZE) {
            this.snackBar.open(`File named ${ file.name } is too large. Max size is 10MB`, 'Close', {
                duration: 2000,
            });

            return false;
        }
        return true;
    }
}
