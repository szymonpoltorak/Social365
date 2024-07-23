import { Injectable } from '@angular/core';
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Optional } from "@core/types/profile/optional.type";

@Injectable({
    providedIn: 'root'
})
export class FileService {

    private readonly MAX_FILE_SIZE: number = 10485760;
    private _allowedFileTypes: string[] = [
        'image/jpeg',
        'image/png',
    ];

    constructor(private snackBar: MatSnackBar) {
    }

    get allowedFileTypes(): string[] {
        return this._allowedFileTypes;
    }

    processAttachSingleFileEvent(event: any): Optional<AttachImage> {
        const val: Optional<AttachImage[]> = this._processAttachFilesEvent(event, 1);

        return val === null ? null : val[0];
    }

    processAttachFilesEvent(event: any): Optional<AttachImage[]> {
        return this._processAttachFilesEvent(event, 10);
    }

    private _processAttachFilesEvent(event: any, maxNumOfFiles: number): Optional<AttachImage[]> {
        const files: File[] = event.target.files as File[];
        const attachedImages: AttachImage[] = [];

        if (files.length > maxNumOfFiles) {
            this.snackBar.open(`You can only attach up to ${ maxNumOfFiles } images!`, 'Close', {
                duration: 2000,
            });

            return null;
        }

        for (const file of files) {
            if (!this.shouldContinue(file)) {
                continue;
            }
            attachedImages.push({
                fileUrl: URL.createObjectURL(file),
                file: file,
            });
        }

        if (attachedImages.length > 0) {
            this.snackBar.open(`Successfully attached ${ attachedImages.length } images!`, 'Close', {
                duration: 2000,
            });
        }
        return attachedImages;
    }

    private shouldContinue(file: File): boolean {
        if (this._allowedFileTypes.indexOf(file.type) === -1) {
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
