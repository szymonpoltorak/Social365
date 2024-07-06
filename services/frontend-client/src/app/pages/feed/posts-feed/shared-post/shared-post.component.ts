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
import { PostComment } from "@interfaces/feed/post-comment.interface";
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
        PostImageViewerComponent
    ],
    templateUrl: './shared-post.component.html',
    styleUrl: './shared-post.component.scss'
})
export class SharedPostComponent implements OnInit {
    @Input({ transform: (value: Either<Post, SharedPost>) => value as SharedPost })
    protected post !: SharedPost;
    protected comments !: CassandraPage<PostComment>;
    protected areCommentsVisible: boolean = false;
    protected currentUser !: Profile;
    @Output() sharePostEvent: EventEmitter<SharePostData> = new EventEmitter<SharePostData>();
    @Output() deletePostEvent: EventEmitter<Post> = new EventEmitter<Post>();

    constructor(private localStorage: LocalStorageService,
                private postService: PostService,
                private commentService: CommentService,
                public dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();

        this.commentService
            .getCommentsForPost(this.post.sharingPost.postId, this.currentUser.profileId, 5, null)
            .subscribe((comments: CassandraPage<PostComment>) => {
                this.comments = comments;
            });
    }

    likePost(): void {
        this.post.sharingPost.isPostLiked = !this.post.sharingPost.isPostLiked;

        this.post.sharingPost.statistics.likes = this.post.sharingPost.isPostLiked ? this.post
            .sharingPost.statistics.likes + 1 : this.post.sharingPost.statistics.likes - 1;
    }

    getCommentsForPost(): void {
        this.areCommentsVisible = !this.areCommentsVisible;
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
                this.sharePostEvent.emit({
                    post: this.post.sharedPost,
                    content: content
                });
            });
    }

    editPost(event: EditDialogOutput): void {
        const request: EditPostRequest = {
            profileId: this.currentUser.profileId,
            postId: this.post.sharingPost.postId,
            content: event.content,
            hasAttachments: false,
            creationDateTime: this.post.sharingPost.creationDateTime
        }

        this.postService
            .editPost(request)
            .subscribe(() => {
                this.post.sharingPost.content = event.content;
            });
    }

}
