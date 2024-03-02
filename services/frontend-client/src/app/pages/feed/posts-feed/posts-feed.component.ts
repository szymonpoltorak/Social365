import { Component } from '@angular/core';
import { MatCard, MatCardActions } from "@angular/material/card";
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatDivider } from "@angular/material/divider";
import { MatButton, MatFabButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { PostComponent } from "@pages/feed/posts-feed/post/post.component";
import { Post } from "@core/data/feed/Post";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";

@Component({
    selector: 'app-posts-feed',
    standalone: true,
    imports: [
        MatCard,
        MatFormField,
        MatInput,
        MatLabel,
        MatDivider,
        MatIcon,
        MatFabButton,
        PostComponent,
        MatButton,
        MatCardActions
    ],
    templateUrl: './posts-feed.component.html',
    styleUrl: './posts-feed.component.scss'
})
export class PostsFeedComponent {
    protected posts: Post[] = [
        {
            postId: 1,
            content: "The Shiba Inu is the smallest of the six original and distinct spitz breeds of dog from Japan.\n" +
                "            A small, agile dog that copes very well with mountainous terrain, the Shiba Inu was originally\n" +
                "            bred for hunting.",
            authorFullName: "Shiba Inu",
            creationDateTime: new Date(),
            likes: 445,
            imageLink: "https://material.angular.io/assets/img/examples/shiba2.jpg",
            comments: 155,
            shares: 25,
            isPostLiked: true,
            isBookmarked: false,
            areNotificationTurnedOn: true,
            postAuthorIconLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            postId: 2,
            content: "The Shiba Inu is medium small, compact. I love being around people and I am very loyal to my family.",
            authorFullName: "Shiba Inu",
            creationDateTime: new Date("2021-01-01T12:00:00"),
            likes: 225,
            imageLink: "",
            comments: 112,
            shares: 79,
            isPostLiked: false,
            isBookmarked: true,
            areNotificationTurnedOn: false,
            postAuthorIconLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        }
    ];
}
