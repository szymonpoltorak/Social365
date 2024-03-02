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
import { CommentComponent } from "@pages/feed/posts-feed/comment/comment.component";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { MatTooltip } from "@angular/material/tooltip";
import { DatePipe } from "@angular/common";
import { MatFormField, MatHint, MatLabel, MatSuffix } from "@angular/material/form-field";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { MatInput } from "@angular/material/input";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";

@Component({
    selector: 'app-post',
    standalone: true,
    imports: [
        MatCard,
        MatButton,
        MatCardActions,
        MatCardAvatar,
        MatCardContent,
        MatCardHeader,
        MatCardImage,
        MatCardSubtitle,
        MatCardTitle,
        MatIcon,
        MatDivider,
        MatIconButton,
        MatMenu,
        MatMenuItem,
        MatMenuTrigger,
        PostAgePipe,
        CommentComponent,
        AvatarPhotoComponent,
        MatTooltip,
        DatePipe,
        MatFormField,
        ReactiveFormsModule,
        MatInput,
        MatLabel,
        MatSuffix,
        MatHint,
        CdkTextareaAutosize
    ],
    templateUrl: './post.component.html',
    styleUrl: './post.component.scss'
})
export class PostComponent implements OnInit {
    @Input() post !: Post;
    protected contentControl: FormControl<any> = new FormControl<string>("", []);
    protected areCommentsVisible: boolean = false;
    comments: PostComment[] = [];

    ngOnInit(): void {
        this.comments = [
            {
                commentId: 1,
                commentLikesCount: 5,
                content: "This is a great post!",
                authorFullName: "John Doe",
                creationDateTime: new Date(),
                profileImageLink: "https://material.angular.io/assets/img/examples/shiba2.jpg",
                isLiked: false
            },
            {
                commentId: 2,
                commentLikesCount: 15,
                content: "I love this post! Especially the part about the new Angular version!",
                authorFullName: "Jacek Kowalski",
                creationDateTime: new Date("2021-01-01T12:00:00"),
                profileImageLink: "https://material.angular.io/assets/img/examples/shiba2.jpg",
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
