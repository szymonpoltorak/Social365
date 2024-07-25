import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatCard, MatCardActions, MatCardContent, MatCardImage } from "@angular/material/card";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { Post } from "@interfaces/feed/post.interface";
import { PostComment } from "@interfaces/posts-comments/post-comment.interface";
import { CommentComponent } from "@pages/feed/posts-feed/post/comment/comment.component";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { ReactiveFormsModule } from "@angular/forms";
import { MatInput } from "@angular/material/input";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { PostHeaderComponent } from "@pages/feed/posts-feed/post/post-header/post-header.component";
import { CommentCreateComponent } from "@pages/feed/posts-feed/post/comment-create/comment-create.component";
import { Profile } from "@interfaces/feed/profile.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { Either } from "@core/types/feed/either.type";
import { SharedPost } from "@interfaces/feed/shared-post.interface";
import { MatDialog } from "@angular/material/dialog";
import { take } from 'rxjs';
import {
    CreateSharePostDialogComponent
} from "@pages/feed/posts-feed/create-share-post-dialog/create-share-post-dialog.component";
import { NgOptimizedImage } from "@angular/common";
import { PostImageViewerComponent } from "@shared/post-image-viewer/post-image-viewer.component";
import { PostService } from "@api/posts-comments/post.service";
import { SharePostData } from "@interfaces/posts-comments/share-post-data.interface";
import { EditDialogOutput } from "@interfaces/posts-comments/edit-dialog-output.interface";
import { ImagesService } from "@api/images/images.service";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { EditPostRequest } from "@interfaces/posts-comments/edit-post-request.interface";
import { CommentService } from "@api/posts-comments/comment.service";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { CommentCreateData } from "@interfaces/posts-comments/comment-create-data.interface";
import { CommentAddRequest } from "@interfaces/posts-comments/comment-add-request.interface";
import { CommentDeleteRequest } from "@interfaces/posts-comments/comment-delete-request.interface";
import { MatProgressBar } from "@angular/material/progress-bar";

@Component({
    selector: 'app-post',
    standalone: true,
    imports: [
        MatCard,
        PostHeaderComponent,
        MatCardContent,
        MatCardImage,
        MatIcon,
        MatDivider,
        MatCardActions,
        MatButton,
        AvatarPhotoComponent,
        MatFormField,
        MatLabel,
        ReactiveFormsModule,
        CdkTextareaAutosize,
        MatInput,
        MatIconButton,
        PickerComponent,
        CommentComponent,
        MatHint,
        CommentCreateComponent,
        NgOptimizedImage,
        PostImageViewerComponent,
        MatProgressBar
    ],
    templateUrl: './post.component.html',
    styleUrl: './post.component.scss'
})
export class PostComponent implements OnInit {

    @Input({ transform: (value: Either<Post, SharedPost>): Post => value as Post }) post !: Post;
    @Output() sharePostEvent: EventEmitter<SharePostData> = new EventEmitter<SharePostData>();
    @Output() deletePostEvent: EventEmitter<Post> = new EventEmitter<Post>();
    protected comments !: CassandraPage<PostComment>;
    protected areCommentsVisible: boolean = false;
    protected currentUser !: Profile;
    private readonly PAGE_SIZE: number = 5;

    constructor(private localStorage: LocalStorageService,
                private postService: PostService,
                private imagesService: ImagesService,
                private commentService: CommentService,
                public dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();
    }

    likePost(): void {
        this.postService
            .updateLikePostCount(this.currentUser.profileId, this.post.postKey.postId, this.post.postKey.author.profileId)
            .subscribe(() => {
                this.post.isPostLiked = !this.post.isPostLiked;

                this.post.statistics.likes = this.post.isPostLiked ? this.post.statistics.likes + 1 : this.post.statistics.likes - 1;
            });
    }

    getCommentsForPost(): void {
        if (this.areCommentsVisible) {
            this.areCommentsVisible = !this.areCommentsVisible;

            return;
        }
        this.commentService
            .getCommentsForPost(this.post.postKey.postId, this.currentUser.profileId, this.PAGE_SIZE, null)
            .subscribe((comments: CassandraPage<PostComment>) => {
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
                    post: this.post,
                    content: content
                });
            });
    }

    editPost(event: EditDialogOutput): void {
        const hasAttachments: boolean = event.newUrls.length > 0;
        const request: EditPostRequest = {
            authorId: this.currentUser.profileId,
            postId: this.post.postKey.postId,
            content: event.content,
            hasAttachments: hasAttachments,
        }

        event.deletedImages.forEach((url: string) => {
            this.imagesService
                .deletePostImageByUrl(url)
                .subscribe();
        });

        event.addedImages.forEach((image: AttachImage) => {
            this.imagesService
                .uploadPostImage(this.currentUser.username, image, this.post.postKey.postId)
                .subscribe();
        });

        this.postService
            .editPost(request)
            .subscribe(() => {
                this.post.content = event.content;

                this.post.imageUrls = event.newUrls.map((image: AttachImage) => image.fileUrl);
            });
    }

    createComment(event: CommentCreateData): void {
        const request: CommentAddRequest = {
            profileId: this.currentUser.profileId,
            postId: this.post.postKey.postId,
            hasAttachment: event.attachedImage !== null,
            content: event.content
        };
        const image: AttachImage = event.attachedImage as AttachImage;

        this.commentService
            .addCommentToPost(request)
            .subscribe((comment: PostComment) => {
                if (request.hasAttachment) {
                    this.imagesService
                        .uploadCommentImage(this.currentUser.username, image, comment.commentKey.commentId)
                        .subscribe();

                    comment.imageUrl = image.fileUrl;
                }
                this.comments.data.unshift(comment);
            });
    }

    loadMoreComments(): void {
        this.commentService
            .getCommentsForPost(this.post.postKey.postId, this.currentUser.profileId, this.PAGE_SIZE, this.comments.pagingState)
            .subscribe((comments: CassandraPage<PostComment>) => {
                this.comments.pagingState = comments.pagingState;
                this.comments.friendsPageNumber = comments.friendsPageNumber;
                this.comments.hasNextPage = comments.hasNextPage;
                this.comments.data.push(...comments.data);
            });
    }

    deleteComment(event: CommentDeleteRequest): void {
        this.commentService
            .deleteComment(event)
            .subscribe(() => {
                this.comments.data = this.comments.data
                    .filter((comment: PostComment) => comment.commentKey.commentId !== event.commentKey.commentId);
            });
    }
}
