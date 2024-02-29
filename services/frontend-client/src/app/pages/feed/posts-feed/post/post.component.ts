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
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { PostAgePipe } from "@core/pipes/post-age.pipe";

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
        MatIconButton,
        MatMenu,
        MatMenuItem,
        MatMenuTrigger,
        PostAgePipe
    ],
    templateUrl: './post.component.html',
    styleUrl: './post.component.scss'
})
export class PostComponent {
    @Input() post !: Post;

    likePost(): void {
        this.post.isPostLiked = !this.post.isPostLiked;

        this.post.likes = this.post.isPostLiked ? this.post.likes + 1 : this.post.likes - 1;
    }
}
