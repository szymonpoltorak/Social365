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
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { MatProgressBar } from "@angular/material/progress-bar";
import { CommentCreateData } from "@interfaces/posts-comments/comment-create-data.interface";
import { CommentDeleteRequest } from "@interfaces/posts-comments/comment-delete-request.interface";
import { CommentAddRequest } from "@interfaces/posts-comments/comment-add-request.interface";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { ImagesService } from "@api/images/images.service";

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
    private readonly PAGE_SIZE: number = 5;
    protected comments !: CassandraPage<PostComment>;
    protected areCommentsVisible: boolean = false;
    protected currentUser !: Profile;

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
        this.post.sharingPost.isPostLiked = !this.post.sharingPost.isPostLiked;

        this.post.sharingPost.statistics.likes = this.post.sharingPost.isPostLiked ? this.post
            .sharingPost.statistics.likes + 1 : this.post.sharingPost.statistics.likes - 1;
    }

    getCommentsForPost(): void {
        this.commentService
            .getCommentsForPost(this.post.sharingPost.postKey.postId, this.currentUser.profileId, 5, null)
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
            profileId: this.currentUser.profileId,
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
                        .uploadCommentImage(this.currentUser.username, image, comment.commentKey.commentId)
                        .subscribe();

                    comment.imageUrl = image.fileUrl;
                }
                this.comments.data.unshift(comment);
            });
    }

    loadMoreComments(): void {
        this.commentService
            .getCommentsForPost(this.post.sharingPost.postKey.postId, this.currentUser.profileId, this.PAGE_SIZE, this.comments.pagingState)
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
