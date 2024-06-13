import { Component, Input } from '@angular/core';
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from "@angular/material/icon";
import { ImageDialogComponent } from "@shared/post-image-viewer/image-dialog/image-dialog.component";

@Component({
    selector: 'app-post-image-viewer',
    standalone: true,
    imports: [
        MatButtonModule,
        MatCardModule,
        MatIconModule
    ],
    templateUrl: './post-image-viewer.component.html',
    styleUrl: './post-image-viewer.component.scss'
})
export class PostImageViewerComponent {

    @Input() imageUrls !: string[];

    constructor(protected matDialog: MatDialog) {
    }

    showImage(imageIndex: number): void {
        this.matDialog.open(ImageDialogComponent, {
            data: {
                imageUrls: this.imageUrls,
                imageIndex: imageIndex
            }
        });
    }
}
