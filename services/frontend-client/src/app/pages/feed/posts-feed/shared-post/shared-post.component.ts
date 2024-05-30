import { Component, Input, OnInit } from '@angular/core';
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
import { User } from "@interfaces/feed/user.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";

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
        PostHeaderComponent
    ],
    templateUrl: './shared-post.component.html',
    styleUrl: './shared-post.component.scss'
})
export class SharedPostComponent implements OnInit {
    @Input({ transform: (value: Either<Post, SharedPost>) => value as SharedPost })
    post !: SharedPost;

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
        this.post.sharingPost.isPostLiked = !this.post.sharingPost.isPostLiked;

        this.post.sharingPost.statistics.likes = this.post.sharingPost.isPostLiked ? this.post
            .sharingPost.statistics.likes + 1 : this.post.sharingPost.statistics.likes - 1;
    }

    getCommentsForPost(): void {
        this.areCommentsVisible = !this.areCommentsVisible;
    }
}
