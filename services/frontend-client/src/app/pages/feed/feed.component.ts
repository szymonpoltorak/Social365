import { Component } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { ProfileFeedComponent } from "@pages/feed/profile-feed/profile-feed.component";
import { PostsFeedComponent } from "@pages/feed/posts-feed/posts-feed.component";
import { FriendsFeedComponent } from "@pages/feed/friends-feed/friends-feed.component";
import { Post } from "@interfaces/feed/post.interface";
import { Either } from "@core/types/feed/either.type";
import { SharedPost } from "@interfaces/feed/shared-post.interface";

@Component({
    selector: 'app-feed',
    standalone: true,
    imports: [
        ToolbarComponent,
        ProfileFeedComponent,
        PostsFeedComponent,
        FriendsFeedComponent
    ],
    templateUrl: './feed.component.html',
    styleUrl: './feed.component.scss'
})
export class FeedComponent {
    protected posts: Either<Post, SharedPost>[] = [
        {
            postId: 1,
            content: "The Shiba Inu is the smallest of the six original and distinct spitz breeds of dog from Japan.\n" +
                "            A small, agile dog that copes very well with mountainous terrain, the Shiba Inu was originally\n" +
                "            bred for hunting.",
            author: {
                profileId: "1",
                fullName: "Shiba Inu",
                subtitle: "Software Developer",
                bio: "I am a simple man",
                username: "shiba-inu@gmail.com",
                profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba1.jpg"
            },
            creationDateTime: new Date(),
            statistics: {
                likes: 445,
                comments: 155,
                shares: 25,
            },
            isPostLiked: true,
            isBookmarked: false,
            areNotificationTurnedOn: true,
            imageUrls: [
                "https://material.angular.io/assets/img/examples/shiba2.jpg",
                "https://material.angular.io/assets/img/examples/shiba1.jpg",
                "https://material.angular.io/assets/img/examples/shiba2.jpg",
            ],
        },
        {
            postId: 1,
            content: "The Shiba Inu is the smallest of the six original and distinct spitz breeds of dog from Japan.\n" +
                "            A small, agile dog that copes very well with mountainous terrain, the Shiba Inu was originally\n" +
                "            bred for hunting.",
            author: {
                profileId: "1",
                fullName: "Shiba Inu",
                subtitle: "Software Developer",
                username: "shiba-inu@gmail.com",
                bio: "I am a simple man",
                profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba1.jpg"
            },
            creationDateTime: new Date(),
            statistics: {
                likes: 445,
                comments: 155,
                shares: 25,
            },
            isPostLiked: true,
            isBookmarked: false,
            areNotificationTurnedOn: true,
            imageUrls: [
                "https://material.angular.io/assets/img/examples/shiba2.jpg",
                "https://material.angular.io/assets/img/examples/shiba1.jpg",
                "https://material.angular.io/assets/img/examples/shiba2.jpg",
                "https://material.angular.io/assets/img/examples/shiba2.jpg",
            ],
        },
        {
            postId: 1,
            content: "The Shiba Inu is the smallest of the six original and distinct spitz breeds of dog from Japan.\n" +
                "            A small, agile dog that copes very well with mountainous terrain, the Shiba Inu was originally\n" +
                "            bred for hunting.",
            author: {
                profileId: "1",
                fullName: "Shiba Inu",
                subtitle: "Software Developer",
                bio: "I am a simple man",
                username: "shiba-inu@gmail.com",
                profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba1.jpg"
            },
            creationDateTime: new Date(),
            statistics: {
                likes: 445,
                comments: 155,
                shares: 25,
            },
            isPostLiked: true,
            isBookmarked: false,
            areNotificationTurnedOn: true,
            imageUrls: [
                "https://material.angular.io/assets/img/examples/shiba2.jpg",
                "https://material.angular.io/assets/img/examples/shiba1.jpg",
            ],
        },
        new SharedPost(
            {
                postId: 3,
                content: "The Shiba Inu is medium small, compact. I love being around people and I am very loyal to my family.",
                author: {
                    profileId: "1",
                    fullName: "James Doe",
                    subtitle: "Software Developer",
                    username: "james@gmail.com",
                    bio: "I am a simple man",
                    profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba1.jpg"
                },
                creationDateTime: new Date("2021-01-01T12:00:00"),
                statistics: {
                    likes: 225,
                    comments: 112,
                    shares: 79,
                },
                isPostLiked: false,
                isBookmarked: true,
                areNotificationTurnedOn: false,
                imageUrls: [],
            },
            {
                postId: 1,
                content: "The Shiba Inu is the smallest of the six original and distinct spitz breeds of dog from Japan.\n" +
                    "A small, agile dog that copes very well with mountainous terrain, the Shiba Inu was originally\n" +
                    "bred for hunting.",
                author: {
                    profileId: "1",
                    fullName: "Shiba Inu",
                    bio: "I am a simple man",
                    subtitle: "Software Developer",
                    username: "shiba-inu@gmail.com",
                    profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba1.jpg"
                },
                creationDateTime: new Date(),
                statistics: {
                    likes: 445,
                    comments: 155,
                    shares: 25,
                },
                isPostLiked: true,
                isBookmarked: false,
                areNotificationTurnedOn: true,
                imageUrls: ["https://material.angular.io/assets/img/examples/shiba2.jpg"],
            }
        ),
        {
            postId: 1,
            content: "The Shiba Inu is the smallest of the six original and distinct spitz breeds of dog from Japan.\n" +
                "            A small, agile dog that copes very well with mountainous terrain, the Shiba Inu was originally\n" +
                "            bred for hunting.",
            author: {
                profileId: "1",
                fullName: "Shiba Inu",
                subtitle: "Software Developer",
                username: "shiba-inu@gmail.com",
                bio: "I am a simple man",
                profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba1.jpg"
            },
            creationDateTime: new Date(),
            statistics: {
                likes: 445,
                comments: 155,
                shares: 25,
            },
            isPostLiked: true,
            isBookmarked: false,
            areNotificationTurnedOn: true,
            imageUrls: ["https://material.angular.io/assets/img/examples/shiba2.jpg"],
        },
        {
            postId: 2,
            content: "The Shiba Inu is medium small, compact. I love being around people and I am very loyal to my family.",
            author: {
                profileId: "1",
                fullName: "Shiba Inu",
                subtitle: "Software Developer",
                bio: "I am a simple man",
                username: "shiba-inu@gmail.com",
                profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba1.jpg"
            },
            creationDateTime: new Date("2021-01-01T12:00:00"),
            statistics: {
                likes: 225,
                comments: 112,
                shares: 79,
            },
            isPostLiked: false,
            isBookmarked: true,
            areNotificationTurnedOn: false,
            imageUrls: [],
        }
    ];

}
