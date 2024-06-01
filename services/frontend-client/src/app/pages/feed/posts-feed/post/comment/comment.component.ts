import { Component, Input } from '@angular/core';
import { PostComment } from "@interfaces/feed/post-comment.interface";
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
    // TODO: remove this when the backend is ready
    @Input() isReply: boolean = false;
    @Input() replyLevel: number = 3;
    replyComments: PostComment[] = [];

    onLikeComment(): void {
        this.comment.isLiked = !this.comment.isLiked;
        this.comment.commentLikesCount = this.comment.isLiked ? this.comment.commentLikesCount + 1 : this.comment.commentLikesCount - 1;
    }
}
