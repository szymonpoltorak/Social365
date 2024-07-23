import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { MatCard } from "@angular/material/card";
import { MatFabButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { FileService } from "@services/utils/file.service";
import { Optional } from "@core/types/profile/optional.type";

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
export class DropImageComponent implements OnInit {

    @ViewChild('fileInput', { static: false }) fileInput!: ElementRef;
    @Input() isAttachingImagesOpened: boolean = false;
    @Output() attachedImagesLength: EventEmitter<number> = new EventEmitter<number>();
    @Input() imageUrls!: string[];
    protected attachedImages: AttachImage[] = [];

    constructor(protected fileService: FileService) {
    }

    ngOnInit(): void {
        if (this.imageUrls === undefined) {
            return;
        }
        this.attachedImages = this.imageUrls.map((imageUrl: string) => {
            return {
                fileUrl: imageUrl,
                file: new File([], ""),
            };
        });
    }

    onFormSubmit(): AttachImage[] {
        const images: AttachImage[] = this.attachedImages;

        this.attachedImages = [];

        return images;
    }

    handleChange(event: any): void {
        const attachedFiles: Optional<AttachImage[]> = this.fileService.processAttachFilesEvent(event);

        if (attachedFiles === null) {
            return;
        }
        this.attachedImages = attachedFiles;

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
