import { Component, Input } from '@angular/core';
import { PostComment } from "@core/data/feed/PostComment";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { PostAgePipe } from "@core/pipes/post-age.pipe";
import { MatRipple } from "@angular/material/core";
import { DatePipe, NgClass } from "@angular/common";
import { MatTooltip } from "@angular/material/tooltip";

@Component({
    selector: 'app-comment',
    standalone: true,
    imports: [
        AvatarPhotoComponent,
        PostAgePipe,
        MatRipple,
        NgClass,
        MatTooltip,
        DatePipe
    ],
    templateUrl: './comment.component.html',
    styleUrl: './comment.component.scss'
})
export class CommentComponent {
    @Input() comment!: PostComment;

    onLikeComment(): void {
        this.comment.isLiked = !this.comment.isLiked;
        this.comment.commentLikesCount = this.comment.isLiked ? this.comment.commentLikesCount + 1 : this.comment.commentLikesCount - 1;
    }
}
