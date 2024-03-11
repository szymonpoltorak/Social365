import { Component } from '@angular/core';
import { PostsFeedComponent } from "@pages/feed/posts-feed/posts-feed.component";
import { Post } from "@core/data/feed/Post";
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { Profile } from "@core/data/feed/Profile";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatFormField } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { User } from "@core/data/feed/User";

@Component({
    selector: 'app-profile-posts',
    standalone: true,
    imports: [
        PostsFeedComponent,
        MatCard,
        MatCardContent,
        MatCardTitle,
        MatCardHeader,
        MatIconButton,
        MatIcon,
        MatFormField,
        MatInput,
        CdkTextareaAutosize,
        ReactiveFormsModule,
        MatButton
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
            comments: 112,
            shares: 79,
            isPostLiked: false,
            isBookmarked: true,
            areNotificationTurnedOn: false,
        }
    ];
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
    protected isEditing: boolean = false;
    protected bioControl: FormControl<string | null> = new FormControl(this.profile.description);
    protected items: Post[] = [
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
    ];
}
