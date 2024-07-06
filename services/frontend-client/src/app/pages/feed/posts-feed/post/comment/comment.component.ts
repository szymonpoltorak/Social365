import { Component, Input, OnInit } from '@angular/core';
import { PostComment } from "@interfaces/feed/post-comment.interface";
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
    @Input() comment!: PostComment;
    @Input() replyLevel: number = 3;
    replyComments !: CassandraPage<PostComment>;
    currentUser !: Profile;
    isMakingReply: boolean = false;
    areRepliesVisible: boolean = false;

    constructor(private localStorage: LocalStorageService,
                private commentService: CommentService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();
    }

    onLikeComment(): void {
        this.comment.isLiked = !this.comment.isLiked;

        this.comment.commentLikesCount = this.comment.isLiked ? this.comment.commentLikesCount + 1 : this.comment.commentLikesCount - 1;
    }

    loadReplies(): void {
        this.areRepliesVisible = true;

        this.commentService
            .getRepliesForComment(this.comment.commentKey.commentId, this.currentUser.profileId, this.PAGE_SIZE, null)
            .subscribe((replies: CassandraPage<PostComment>) => {
                this.replyComments = replies;
            });
    }
}
