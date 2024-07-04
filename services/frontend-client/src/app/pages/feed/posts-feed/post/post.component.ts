import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatCard, MatCardActions, MatCardContent, MatCardImage } from "@angular/material/card";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { Post } from "@interfaces/feed/post.interface";
import { PostComment } from "@interfaces/feed/post-comment.interface";
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
        PostImageViewerComponent
    ],
    templateUrl: './post.component.html',
    styleUrl: './post.component.scss'
})
export class PostComponent implements OnInit {
    @Input({ transform: (value: Either<Post, SharedPost>): Post => value as Post })
    post !: Post;
    protected comments: PostComment[] = [];
    protected areCommentsVisible: boolean = false;
    protected currentUser !: Profile;
    @Output() sharePostEvent: EventEmitter<SharePostData> = new EventEmitter<SharePostData>();
    @Output() deletePostEvent: EventEmitter<Post> = new EventEmitter<Post>();

    constructor(private localStorage: LocalStorageService,
                private postService: PostService,
                private imagesService: ImagesService,
                public dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.comments = [
            {
                commentId: "1",
                commentLikesCount: 5,
                content: "This is a great post!",
                author: {
                    profileId: "1",
                    fullName: "John Doe",
                    subtitle: "Software Developer",
                    bio: "I am a simple man",
                    username: "shiba@gmail.com",
                    profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba2.jpg"
                },
                creationDateTime: new Date(),
                isLiked: false
            },
            {
                commentId: "2",
                commentLikesCount: 15,
                content: "I love this post! Especially the part about the new Angular version!",
                author: {
                    profileId: "1",
                    fullName: "Jacek Kowalski",
                    subtitle: "Business Analyst",
                    bio: "I am a simple man",
                    username: "shiba@gmail.com",
                    profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba2.jpg"
                },
                creationDateTime: new Date("2021-01-01T12:00:00"),
                isLiked: true
            }
        ];
        this.currentUser = this.localStorage.getUserProfileFromStorage();
    }

    likePost(): void {
        this.postService
            .updateLikePostCount(this.currentUser.profileId, this.post.postId, this.post.creationDateTime)
            .subscribe(() => {
                this.post.isPostLiked = !this.post.isPostLiked;

                this.post.statistics.likes = this.post.isPostLiked ? this.post.statistics.likes + 1 : this.post.statistics.likes - 1;
            });
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
                console.log(content);

                this.sharePostEvent.emit({
                    post: this.post,
                    content: content
                });
            });
    }

    editPost(event: EditDialogOutput): void {
        const hasAttachments: boolean = event.newUrls.length > 0;
        const request: EditPostRequest = {
            profileId: this.currentUser.profileId,
            postId: this.post.postId,
            content: event.content,
            hasAttachments: hasAttachments,
            creationDateTime: this.post.creationDateTime
        }

        event.deletedImages.forEach((url: string) => {
            this.imagesService
                .deleteImageByUrl(url)
                .subscribe();
        });

        event.addedImages.forEach((image: AttachImage) => {
            this.imagesService
                .uploadPostImage(this.currentUser.username, image, this.post.postId)
                .subscribe();
        });

        this.postService
            .editPost(request)
            .subscribe(() => {
                this.post.content = event.content;

                this.post.imageUrls = event.newUrls.map((image: AttachImage) => image.fileUrl);
            });
    }
}
