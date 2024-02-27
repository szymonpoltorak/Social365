import { Component, Input } from '@angular/core';
import {
    MatCard,
    MatCardActions,
    MatCardAvatar,
    MatCardContent,
    MatCardHeader,
    MatCardImage,
    MatCardSubtitle,
    MatCardTitle
} from "@angular/material/card";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { Post } from "@core/data/feed/Post";

@Component({
    selector: 'app-post',
    standalone: true,
    imports: [
        MatCard,
        MatButton,
        MatCardActions,
        MatCardAvatar,
        MatCardContent,
        MatCardHeader,
        MatCardImage,
        MatCardSubtitle,
        MatCardTitle,
        MatIcon,
        MatDivider,
        MatIconButton
    ],
    templateUrl: './post.component.html',
    styleUrl: './post.component.scss'
})
export class PostComponent {
    @Input() post !: Post;
}
