import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SharedPost } from "@interfaces/feed/shared-post.interface";
import { Either } from "@core/types/feed/either.type";
import { Post } from "@interfaces/feed/post.interface";
import { CommentComponent } from "@pages/feed/posts-feed/post/comment/comment.component";
import { CommentCreateComponent } from "@pages/feed/posts-feed/post/comment-create/comment-create.component";
import { MatButton } from "@angular/material/button";
import { MatCard, MatCardActions, MatCardContent, MatCardImage } from "@angular/material/card";
import { MatDivider } from "@angular/material/divider";
import { MatIcon } from "@angular/material/icon";
import { PostHeaderComponent } from "@pages/feed/posts-feed/post/post-header/post-header.component";
import { PostComment } from "@interfaces/posts-comments/post-comment.interface";
import { Profile } from "@interfaces/feed/profile.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import {
    CreateSharePostDialogComponent
} from "@pages/feed/posts-feed/create-share-post-dialog/create-share-post-dialog.component";
import { take } from "rxjs";
import { MatDialog } from "@angular/material/dialog";
import { PostImageViewerComponent } from "@shared/post-image-viewer/post-image-viewer.component";
import { SharePostData } from "@interfaces/posts-comments/share-post-data.interface";
import { EditDialogOutput } from "@interfaces/posts-comments/edit-dialog-output.interface";
import { PostService } from "@api/posts-comments/post.service";
import { EditPostRequest } from "@interfaces/posts-comments/edit-post-request.interface";
import { CommentService } from "@api/posts-comments/comment.service";
import { MatProgressBar } from "@angular/material/progress-bar";
import { CommentCreateData } from "@interfaces/posts-comments/comment-create-data.interface";
import { CommentAddRequest } from "@interfaces/posts-comments/comment-add-request.interface";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { ImagesService } from "@api/images/images.service";
import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";
import { SocialPage } from "@core/utils/social-page";
import { CommentsPagingState } from "@core/utils/comments-paging-state";

@Component({
    selector: 'app-shared-post',
    standalone: true,
    imports: [
        CommentComponent,
        CommentCreateComponent,
        MatButton,
        MatCard,
        MatCardActions,
        MatCardContent,
        MatCardImage,
        MatDivider,
        MatIcon,
        PostHeaderComponent,
        PostImageViewerComponent,
        MatProgressBar
    ],
    templateUrl: './shared-post.component.html',
    styleUrl: './shared-post.component.scss'
})
export class SharedPostComponent implements OnInit {

    @Input({ transform: (value: Either<Post, SharedPost>) => value as SharedPost }) post !: SharedPost;
    @Output() sharePostEvent: EventEmitter<SharePostData> = new EventEmitter<SharePostData>();
    @Output() deletePostEvent: EventEmitter<Post> = new EventEmitter<Post>();
    protected comments !: SocialPage<PostComment, CommentsPagingState>;
    protected areCommentsVisible: boolean = false;
    protected currentUser !: Profile;
    private readonly PAGE_SIZE: number = 5;

    constructor(private localStorage: LocalStorageService,
                private postService: PostService,
                private commentService: CommentService,
                private imagesService: ImagesService,
                public dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();
    }

    likePost(): void {
        this.post.sharingPost.updateLikeCount();
    }

    getCommentsForPost(): void {
        this.commentService
            .getCommentsForPost(this.post.sharingPost.postKey.postId, new CommentsPagingState(this.PAGE_SIZE, null))
            .subscribe((comments: SocialPage<PostComment, CommentsPagingState>) => {
                this.areCommentsVisible = !this.areCommentsVisible;

                this.comments = comments;
            });
    }

    sharePost(): void {
        const createDialog = this.dialog.open(CreateSharePostDialogComponent, {
            minHeight: '100px',
            minWidth: '320px',
            exitAnimationDuration: 100,
        });

        createDialog
            .afterClosed()
            .pipe(take(1))
            .subscribe((content: string) => {
                if (content === undefined) {
                    return;
                }
                this.sharePostEvent.emit({
                    post: this.post.sharedPost,
                    content: content
                });
            });
    }

    editPost(event: EditDialogOutput): void {
        const request: EditPostRequest = {
            authorId: this.currentUser.profileId,
            postId: this.post.sharingPost.postKey.postId,
            content: event.content,
            hasAttachments: false,
        }

        this.postService
            .editPost(request)
            .subscribe(() => {
                this.post.sharingPost.content = event.content;
            });
    }

    createComment(event: CommentCreateData): void {
        const request: CommentAddRequest = {
            postId: this.post.sharingPost.postKey.postId,
            hasAttachment: event.attachedImage !== null,
            content: event.content
        };
        const image: AttachImage = event.attachedImage as AttachImage;

        this.commentService
            .addCommentToPost(request)
            .subscribe((comment: PostComment) => {
                if (request.hasAttachment) {
                    this.imagesService
                        .uploadCommentImage(image, comment.commentKey.commentId, comment.commentKey.postId)
                        .subscribe();

                    comment.imageUrl = image.fileUrl;
                }
                this.comments.addFirst(comment);
            });
    }

    loadMoreComments(): void {
        this.commentService
            .getCommentsForPost(this.post.sharingPost.postKey.postId, this.comments.pagingState)
            .subscribe((comments: SocialPage<PostComment, CommentsPagingState>) => this.comments.concatAndUpdate(comments));
    }

    deleteComment(commentKey: CommentKey): void {
        this.commentService
            .deleteComment(commentKey)
            .subscribe((comment: PostComment) => this.comments.remove(comment));
    }
}
