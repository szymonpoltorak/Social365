import { Component, Input, OnInit } from '@angular/core';
import {
    MatCard,
    MatCardActions,
    MatCardAvatar,
    MatCardContent,
    MatCardHeader,
    MatCardImage,
    MatCardSubtitle,
    MatCardTitle
} from "@angular/material/card";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { Post } from "@core/data/feed/Post";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { PostAgePipe } from "@core/pipes/post-age.pipe";
import { PostComment } from "@core/data/feed/PostComment";
import { CommentComponent } from "@pages/feed/posts-feed/post/comment/comment.component";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { MatTooltip } from "@angular/material/tooltip";
import { DatePipe } from "@angular/common";
import { MatFormField, MatHint, MatLabel, MatSuffix } from "@angular/material/form-field";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { MatInput } from "@angular/material/input";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { Author } from "@core/data/feed/Author";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { PostHeaderComponent } from "@pages/feed/posts-feed/post/post-header/post-header.component";
import { CommentCreateComponent } from "@pages/feed/posts-feed/post/comment-create/comment-create.component";

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
    @Input() post !: Post;
    protected areCommentsVisible: boolean = false;
    comments: PostComment[] = [];
    user: Author = {
        id: "1",
        fullName: "Shiba Inu",
        subtitle: "Cutest dog you see today",
        profilePhotoLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
    };

    ngOnInit(): void {
        this.comments = [
            {
                commentId: 1,
                commentLikesCount: 5,
                content: "This is a great post!",
                author: {
                    id: "1",
                    fullName: "John Doe",
                    subtitle: "Software Developer",
                    profilePhotoLink: "https://material.angular.io/assets/img/examples/shiba2.jpg"
                },
                creationDateTime: new Date(),
                isLiked: false
            },
            {
                commentId: 2,
                commentLikesCount: 15,
                content: "I love this post! Especially the part about the new Angular version!",
                author: {
                    id: "1",
                    fullName: "Jacek Kowalski",
                    subtitle: "Business Analyst",
                    profilePhotoLink: "https://material.angular.io/assets/img/examples/shiba2.jpg"
                },
                creationDateTime: new Date("2021-01-01T12:00:00"),
                isLiked: true
            }
        ];
    }

    likePost(): void {
        this.post.isPostLiked = !this.post.isPostLiked;

        this.post.likes = this.post.isPostLiked ? this.post.likes + 1 : this.post.likes - 1;
    }

    getCommentsForPost(): void {
        this.areCommentsVisible = !this.areCommentsVisible;
    }
}
