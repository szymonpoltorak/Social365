import { Component, Input, OnInit } from '@angular/core';
import { PostComment } from "@interfaces/feed/post-comment.interface";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { PostAgePipe } from "@core/pipes/post-age.pipe";
import { MatRipple } from "@angular/material/core";
import { DatePipe, NgClass } from "@angular/common";
import { MatTooltip } from "@angular/material/tooltip";
import { CommentCreateComponent } from "@pages/feed/posts-feed/post/comment-create/comment-create.component";
import { User } from '@core/interfaces/feed/user.interface';
import { LocalStorageService } from "@services/utils/local-storage.service";
import { MatButton } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";

@Component({
    selector: 'app-comment',
    standalone: true,
    imports: [
        AvatarPhotoComponent,
        PostAgePipe,
        MatRipple,
        NgClass,
        MatTooltip,
        DatePipe,
        CommentCreateComponent,
        MatButton,
        MatIconModule
    ],
    templateUrl: './comment.component.html',
    styleUrl: './comment.component.scss'
})
export class CommentComponent implements OnInit {
    @Input() comment!: PostComment;
    // TODO: remove this when the backend is ready
    @Input() isReply: boolean = false;
    @Input() replyLevel: number = 3;
    replyComments: PostComment[] = [];
    currentUser !: User;
    isMakingReply: boolean = false;

    constructor(private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserFromStorage();
        this.replyComments.push(this.comment);
    }

    onLikeComment(): void {
        this.comment.isLiked = !this.comment.isLiked;
        this.comment.commentLikesCount = this.comment.isLiked ? this.comment.commentLikesCount + 1 : this.comment.commentLikesCount - 1;
    }
}
