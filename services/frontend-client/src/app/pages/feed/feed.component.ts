import { Component, OnInit } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { ProfileFeedComponent } from "@pages/feed/profile-feed/profile-feed.component";
import { PostsFeedComponent } from "@pages/feed/posts-feed/posts-feed.component";
import { Profile } from "@interfaces/feed/profile.interface";
import { FriendsFeedComponent } from "@pages/feed/friends-feed/friends-feed.component";
import { LocalStorageService } from "@services/utils/local-storage.service";
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
export class FeedComponent implements OnInit{
    protected profile: Profile = {
        fullName: "John Doe",
        username: "john@gmail.com",
        subtitle: "Web developer at Google",
        description: "I am a simple man with big ambitions. " +
            "I love to code and I am passionate about web development. " +
            "I am a team player and I am always looking for new challenges.",
        postCount: 256,
        numberOfFriends: 1025,
        numberOfFollowers: 300,
        profileImagePath: "https://material.angular.io/assets/img/examples/shiba1.jpg"
    };
    protected posts: Either<Post, SharedPost>[] = [
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
            statistics: {
                likes: 445,
                comments: 155,
                shares: 25,
            },
            isPostLiked: true,
            isBookmarked: false,
            areNotificationTurnedOn: true,
            imageLink: "https://material.angular.io/assets/img/examples/shiba2.jpg",
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
            statistics: {
                likes: 225,
                comments: 112,
                shares: 79,
            },
            isPostLiked: false,
            isBookmarked: true,
            areNotificationTurnedOn: false,
            imageLink: "",
        }
    ];

    constructor(private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        this.localStorage.saveUserToStorage({
            fullName: this.profile.fullName,
            username: this.profile.username,
            subtitle: this.profile.subtitle,
            profileImagePath: this.profile.profileImagePath
        });
    }
}
