import { Component, Input } from '@angular/core';
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { DatePipe } from "@angular/common";
import { MatCardSubtitle, MatCardTitle } from "@angular/material/card";
import { MatIcon } from "@angular/material/icon";
import { MatIconButton } from "@angular/material/button";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { MatTooltip } from "@angular/material/tooltip";
import { PostAgePipe } from "@core/pipes/post-age.pipe";
import { Post } from "@interfaces/feed/post.interface";

@Component({
    selector: 'app-post-header',
    standalone: true,
    imports: [
        AvatarPhotoComponent,
        DatePipe,
        MatCardSubtitle,
        MatCardTitle,
        MatIcon,
        MatIconButton,
        MatMenu,
        MatMenuItem,
        MatTooltip,
        PostAgePipe,
        MatMenuTrigger
    ],
    templateUrl: './post-header.component.html',
    styleUrl: './post-header.component.scss'
})
export class PostHeaderComponent {
    @Input() post !: Post;
    @Input() username !: string;

}
