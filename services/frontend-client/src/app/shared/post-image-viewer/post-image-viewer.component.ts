import { Component, Input } from '@angular/core';
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatIconModule } from "@angular/material/icon";

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

    @Input() imageLinks !: string[];
    currentImage: number = 0;

}
