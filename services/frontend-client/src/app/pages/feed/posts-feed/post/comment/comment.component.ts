import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PostComment } from "@interfaces/posts-comments/post-comment.interface";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { PostAgePipe } from "@core/pipes/post-age.pipe";
import { MatRipple } from "@angular/material/core";
import { DatePipe, NgClass } from "@angular/common";
import { MatTooltip } from "@angular/material/tooltip";
import { CommentCreateComponent } from "@pages/feed/posts-feed/post/comment-create/comment-create.component";
import { Profile } from '@interfaces/feed/profile.interface';
import { LocalStorageService } from "@services/utils/local-storage.service";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { CommentService } from "@api/posts-comments/comment.service";
import { RepliesService } from "@api/posts-comments/replies.service";
import { ReplyComment } from "@interfaces/posts-comments/reply-comment.interface";
import { Either } from "@core/types/feed/either.type";
import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";
import { ReplyKey } from "@interfaces/posts-comments/reply-key.interface";
import { LikeCommentRequest } from "@interfaces/posts-comments/like-comment-request.interface";
import { LikeReplyRequest } from "@interfaces/posts-comments/like-reply-request.interface";
import { ReplyAddRequest } from "@interfaces/posts-comments/reply-add-request.interface";
import { CommentCreateData } from "@interfaces/posts-comments/comment-create-data.interface";
import { ImagesService } from "@api/images/images.service";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { PostImageViewerComponent } from "@shared/post-image-viewer/post-image-viewer.component";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { ReplyDeleteRequest } from "@interfaces/posts-comments/reply-delete-request.interface";
import { CommentDeleteRequest } from "@interfaces/posts-comments/comment-delete-request.interface";
import { CommentEditRequest } from "@interfaces/posts-comments/comment-request.interface";
import { ReplyEditRequest } from "@interfaces/posts-comments/reply-edit-request.interface";
import { RoutingService } from "@services/profile/routing.service";

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
        MatIconModule,
        PostImageViewerComponent,
        MatIconButton,
        MatMenu,
        MatMenuItem,
        MatMenuTrigger
    ],
    templateUrl: './comment.component.html',
    styleUrl: './comment.component.scss'
})
export class CommentComponent implements OnInit {

    @Input() comment!: Either<PostComment, ReplyComment>;
    @Input() replyLevel: number = 3;
    @Output() commentDeleted: EventEmitter<CommentDeleteRequest> = new EventEmitter<CommentDeleteRequest>();
    @Output() replyCommentDeleted: EventEmitter<ReplyDeleteRequest> = new EventEmitter<ReplyDeleteRequest>();
    replyComments !: CassandraPage<ReplyComment>;
    currentUser !: Profile;
    isMakingReply: boolean = false;
    areRepliesVisible: boolean = false;
    creationDateTime !: Date;
    isEditing: boolean = false;
    private readonly PAGE_SIZE: number = 3;

    constructor(private localStorage: LocalStorageService,
                private repliesService: RepliesService,
                protected routingService: RoutingService,
                private imagesService: ImagesService,
                private commentService: CommentService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();
        this.creationDateTime = new Date(this.comment.commentKey.creationDateTime);
    }

    onLikeComment(): void {
        if (this.isPostComment()) {
            const likeCommentRequest: LikeCommentRequest = {
                commentKey: this.comment.commentKey as CommentKey,
                profileId: this.currentUser.profileId
            }
            this.commentService
                .updateLikeCommentCount(likeCommentRequest)
                .subscribe(() => {
                    this.comment.isLiked = !this.comment.isLiked;

                    this.comment.commentLikesCount = this.comment.isLiked ? this.comment.commentLikesCount + 1 : this.comment.commentLikesCount - 1;
                })
        } else {
            const likeReplyRequest: LikeReplyRequest = {
                replyKey: this.comment.commentKey as ReplyKey,
                profileId: this.currentUser.profileId
            }
            this.repliesService
                .updateLikeCommentCount(likeReplyRequest)
                .subscribe(() => {
                    this.comment.isLiked = !this.comment.isLiked;

                    this.comment.commentLikesCount = this.comment.isLiked ? this.comment.commentLikesCount + 1 : this.comment.commentLikesCount - 1;
                })
        }
    }

    loadReplies(): void {
        this.areRepliesVisible = true;

        this.repliesService
            .getRepliesForComment(this.getProperCommentId(), this.currentUser.profileId, this.PAGE_SIZE, null)
            .subscribe((replies: CassandraPage<ReplyComment>) => {
                this.replyComments = replies;
            });
    }

    createReplyComment(event: CommentCreateData): void {
        const replyAddRequest: ReplyAddRequest = {
            profileId: this.currentUser.profileId,
            commentId: this.getProperCommentId(),
            hasAttachment: event.attachedImage !== null,
            content: event.content
        };
        const image: AttachImage = event.attachedImage as AttachImage;

        if (replyAddRequest.hasAttachment) {
            this.imagesService
                .uploadCommentImage(this.currentUser.username, image, replyAddRequest.commentId)
                .subscribe();
        }
        this.repliesService
            .addReplyToComment(replyAddRequest)
            .subscribe((reply: ReplyComment) => {
                if (replyAddRequest.hasAttachment) {
                    reply.imageUrl = image.fileUrl;
                }
                this.replyComments.data.push(reply);
            });
    }

    loadMoreReplies(): void {
        this.repliesService
            .getRepliesForComment(this.getProperCommentId(), this.currentUser.profileId, this.PAGE_SIZE, this.replyComments.pagingState)
            .subscribe((replies: CassandraPage<ReplyComment>) => {
                this.replyComments = replies;
            });
    }

    deleteComment(): void {
        if (this.isPostComment()) {
            const request: CommentDeleteRequest = {
                commentKey: this.comment.commentKey as CommentKey,
                profileId: this.currentUser.profileId
            };

            this.commentDeleted.emit(request);
        } else {
            const request: ReplyDeleteRequest = {
                replyKey: this.comment.commentKey as ReplyKey,
                profileId: this.currentUser.profileId
            };

            this.replyCommentDeleted.emit(request);
        }
    }

    deleteReplyComment(event: ReplyDeleteRequest): void {
        this.repliesService
            .deleteReplyComment(event)
            .subscribe(() => {
                this.replyComments.data = this.replyComments.data
                    .filter((reply: ReplyComment) => reply.commentKey.replyCommentId !== event.replyKey.replyCommentId);
            });
    }

    editComment(event: CommentCreateData): void {
        if (!this.shouldEditComment(event)) {
            this.isEditing = false;

            return;
        }
        if (this.isPostComment()) {
            this.handleCommentEdit(event);
        } else {
            this.handleReplyCommentEdit(event);
        }
        this.isEditing = false;
    }

    private getProperCommentId(): string {
        if (this.isPostComment()) {
            return (this.comment.commentKey as CommentKey).commentId;
        }
        return (this.comment.commentKey as ReplyKey).replyCommentId;
    }

    private isPostComment(): boolean {
        return (this.comment.commentKey as CommentKey).commentId !== undefined;
    }

    private handleCommentEdit(event: CommentCreateData): void {
        const request: CommentEditRequest = {
            commentKey: this.comment.commentKey as CommentKey,
            profileId: this.currentUser.profileId,
            content: event.content,
            hasAttachment: event.attachedImage !== null
        };

        if (event.attachedImage !== null && event.attachedImage.fileUrl !== this.comment.imageUrl) {
            this.imagesService
                .uploadCommentImage(this.currentUser.username, event.attachedImage, request.commentKey.commentId)
                .subscribe();
        } else if (event.attachedImage === null && this.comment.imageUrl !== "") {
            this.imagesService
                .deleteCommentImage(request.commentKey.commentId)
                .subscribe();
        }
        const hasAttachment: boolean = this.comment.imageUrl !== "";

        if (this.comment.content !== event.content || hasAttachment !== request.hasAttachment) {
            this.commentService
                .editComment(request)
                .subscribe(() => {
                    this.comment.content = event.content;
                    this.comment.imageUrl = event.attachedImage !== null ? event.attachedImage.fileUrl : "";
                });
        }
    }

    private handleReplyCommentEdit(event: CommentCreateData): void {
        const request: ReplyEditRequest = {
            replyKey: this.comment.commentKey as ReplyKey,
            profileId: this.currentUser.profileId,
            content: event.content,
            hasAttachment: event.attachedImage !== null
        };

        if (event.attachedImage !== null && event.attachedImage.fileUrl !== this.comment.imageUrl) {
            this.imagesService
                .uploadCommentImage(this.currentUser.username, event.attachedImage, request.replyKey.replyCommentId)
                .subscribe();
        } else if (event.attachedImage === null && this.comment.imageUrl !== "") {
            this.imagesService
                .deleteCommentImage(request.replyKey.replyCommentId)
                .subscribe();
        }
        const hasAttachment: boolean = this.comment.imageUrl !== "";

        if (this.comment.content !== event.content || hasAttachment !== request.hasAttachment) {
            this.repliesService
                .editReplyComment(request)
                .subscribe(() => {
                    this.comment.content = event.content;
                    this.comment.imageUrl = event.attachedImage !== null ? event.attachedImage.fileUrl : "";
                });
        }
    }

    private shouldEditComment(event: CommentCreateData): boolean {
        if (event === undefined) {
            return false;
        }
        if (event.attachedImage === null && this.comment.imageUrl === "" && event.content === this.comment.content) {
            return false;
        }
        return !(event.attachedImage !== null && event.attachedImage.fileUrl === this.comment.imageUrl
            && event.content === this.comment.content);

    }
}
