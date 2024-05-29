import { Component, Input, OnInit } from '@angular/core';
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
import { User } from "@interfaces/feed/user.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { Either } from "@core/types/feed/either.type";
import { SharedPost } from "@interfaces/feed/shared-post.interface";

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
        CommentCreateComponent
    ],
    templateUrl: './post.component.html',
    styleUrl: './post.component.scss'
})
export class PostComponent implements OnInit {
    @Input({ transform: (value: Either<Post, SharedPost>): Post => value as Post })
    post !: Post;

    protected areCommentsVisible: boolean = false;
    comments: PostComment[] = [];
    protected user !: User;

    constructor(private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        this.comments = [
            {
                commentId: "1",
                commentLikesCount: 5,
                content: "This is a great post!",
                author: {
                    fullName: "John Doe",
                    subtitle: "Software Developer",
                    username: "shiba@gmail.com",
                    profileImagePath: "https://material.angular.io/assets/img/examples/shiba2.jpg"
                },
                creationDateTime: new Date(),
                isLiked: false
            },
            {
                commentId: "2",
                commentLikesCount: 15,
                content: "I love this post! Especially the part about the new Angular version!",
                author: {
                    fullName: "Jacek Kowalski",
                    subtitle: "Business Analyst",
                    username: "shiba@gmail.com",
                    profileImagePath: "https://material.angular.io/assets/img/examples/shiba2.jpg"
                },
                creationDateTime: new Date("2021-01-01T12:00:00"),
                isLiked: true
            }
        ];
        this.user = this.localStorage.getUserFromStorage();
    }

    likePost(): void {
        this.post.isPostLiked = !this.post.isPostLiked;

        this.post.statistics.likes = this.post.isPostLiked ? this.post.statistics.likes + 1 : this.post.statistics.likes - 1;
    }

    getCommentsForPost(): void {
        this.areCommentsVisible = !this.areCommentsVisible;
    }
}
