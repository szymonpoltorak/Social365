import { Component, Input, OnInit } from '@angular/core';
import { PostComment } from "@interfaces/posts-comments/post-comment.interface";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { PostAgePipe } from "@core/pipes/post-age.pipe";
import { MatRipple } from "@angular/material/core";
import { DatePipe, NgClass } from "@angular/common";
import { MatTooltip } from "@angular/material/tooltip";
import { CommentCreateComponent } from "@pages/feed/posts-feed/post/comment-create/comment-create.component";
import { Profile } from '@interfaces/feed/profile.interface';
import { LocalStorageService } from "@services/utils/local-storage.service";
import { MatButton } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { CommentService } from "@api/posts-comments/comment.service";
import { RepliesService } from "@api/posts-comments/replies.service";
import { ReplyComment } from "@interfaces/posts-comments/reply-comment.interface";
import { Either } from "@core/types/feed/either.type";
import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";
import { ReplyKey } from "@interfaces/posts-comments/reply-key.interface";

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

    private readonly PAGE_SIZE: number = 3;
    @Input() comment!: Either<PostComment, ReplyComment>;
    @Input() replyLevel: number = 3;
    replyComments !: CassandraPage<ReplyComment>;
    currentUser !: Profile;
    isMakingReply: boolean = false;
    areRepliesVisible: boolean = false;
    creationDateTime !: Date;

    constructor(private localStorage: LocalStorageService,
                private repliesService: RepliesService,
                private commentService: CommentService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();
        this.creationDateTime = new Date(this.comment.commentKey.creationDateTime);
    }

    onLikeComment(): void {
        this.comment.isLiked = !this.comment.isLiked;

        this.comment.commentLikesCount = this.comment.isLiked ? this.comment.commentLikesCount + 1 : this.comment.commentLikesCount - 1;
    }

    loadReplies(): void {
        this.areRepliesVisible = true;

        this.repliesService
            .getRepliesForComment(this.getProperCommentId(), this.currentUser.profileId, this.PAGE_SIZE, null)
            .subscribe((replies: CassandraPage<ReplyComment>) => {
                this.replyComments = replies;
            });
    }

    private getProperCommentId(): string {
        if ((this.comment.commentKey as CommentKey).commentId !== undefined) {
            return (this.comment.commentKey as CommentKey).commentId;
        }
        return (this.comment.commentKey as ReplyKey).replyCommentId;
    }
}
