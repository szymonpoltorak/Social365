import { Component } from '@angular/core';
import { PostsFeedComponent } from "@pages/feed/posts-feed/posts-feed.component";
import { Post } from "@core/data/feed/Post";

@Component({
    selector: 'app-profile-posts',
    standalone: true,
    imports: [
        PostsFeedComponent
    ],
    templateUrl: './profile-posts.component.html',
    styleUrl: './profile-posts.component.scss'
})
export class ProfilePostsComponent {
    protected posts: Post[] = [
        {
            postId: 1,
            content: "The Shiba Inu is the smallest of the six original and distinct spitz breeds of dog from Japan.\n" +
                "            A small, agile dog that copes very well with mountainous terrain, the Shiba Inu was originally\n" +
                "            bred for hunting.",
            author: {
                fullName: "Shiba Inu",
                subtitle: "Software Developer",
                username: "shiba-inu@gmail.com",
                profileImagePath: "https://material.angular.io/assets/img/examples/shiba1.jpg"
            },
            creationDateTime: new Date(),
            likes: 445,
            imageLink: "https://material.angular.io/assets/img/examples/shiba2.jpg",
            comments: 155,
            shares: 25,
            isPostLiked: true,
            isBookmarked: false,
            areNotificationTurnedOn: true,
        },
        {
            postId: 2,
            content: "The Shiba Inu is medium small, compact. I love being around people and I am very loyal to my family.",
            author: {
                fullName: "Shiba Inu",
                subtitle: "Software Developer",
                username: "shiba-inu@gmail.com",
                profileImagePath: "https://material.angular.io/assets/img/examples/shiba1.jpg"
            },
            creationDateTime: new Date("2021-01-01T12:00:00"),
            likes: 225,
            imageLink: "",
            comments: 112,
            shares: 79,
            isPostLiked: false,
            isBookmarked: true,
            areNotificationTurnedOn: false,
        }
    ];

}
