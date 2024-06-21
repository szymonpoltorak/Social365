import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { PostsFeedComponent } from "@pages/feed/posts-feed/posts-feed.component";
import { Post } from "@interfaces/feed/post.interface";
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatFormField } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { RouterPaths } from "@enums/router-paths.enum";
import { Either } from "@core/types/feed/either.type";
import { SharedPost } from "@interfaces/feed/shared-post.interface";
import { AsyncPipe, NgIf } from "@angular/common";
import { ProfileService } from "@api/profile/profile.service";
import { Profile } from "@interfaces/feed/profile.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { MatSnackBar } from '@angular/material/snack-bar';
import { FriendsService } from '@core/services/api/profile/friends.service';
import { Observable, Subject, takeUntil } from "rxjs";
import { Page } from "@interfaces/feed/page.interface";
import { FriendFeedOption } from "@interfaces/feed/friend-feed-option.interface";
import { RoutingService } from '@core/services/profile/routing.service';

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
        MatButton,
        NgIf,
        AsyncPipe
    ],
    templateUrl: './profile-posts.component.html',
    styleUrl: './profile-posts.component.scss'
})
export class ProfilePostsComponent implements OnInit, OnDestroy {
    private readonly FIRST_PAGE: number = 0;
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
            imageUrls: ["https://material.angular.io/assets/img/examples/shiba2.jpg"]
        },
        {
            postId: 2,
            content: "The Shiba Inu is medium small, compact. I love being around people and I am very loyal to my family.",
            author: {
                profileId: "1",
                fullName: "Shiba Inu",
                subtitle: "Software Developer",
                username: "shiba-inu@gmail.com",
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
            imageUrls: []
        }
    ];
    protected profileInfo !: Profile;
    protected isEditing: boolean = false;
    protected bioControl !: FormControl<string | null>;
    private activateRouteDestroy$: Subject<void> = new Subject<void>();
    protected readonly RouterPaths = RouterPaths;
    protected items: Either<Post, SharedPost>[] = [
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
                username: "shiba-inu@gmail.com",
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
            imageUrls: ["https://material.angular.io/assets/img/examples/shiba2.jpg"],
        },
        {
            postId: 2,
            content: "The Shiba Inu is medium small, compact. I love being around people and I am very loyal to my family.",
            author: {
                profileId: "1",
                fullName: "Shiba Inu",
                subtitle: "Software Developer",
                username: "shiba-inu@gmail.com",
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
            imageUrls: ["https://material.angular.io/assets/img/examples/shiba2.jpg"],
        }
    ];
    protected friends !: Observable<Page<FriendFeedOption>>;
    protected numberOfItemsToDisplay: number = 3;
    protected currentUser !: Profile;

    constructor(protected router: Router,
                private routingService: RoutingService,
                private matSnackBar: MatSnackBar,
                private friendsService: FriendsService,
                private localStorage: LocalStorageService,
                private profileService: ProfileService) {
    }

    @HostListener('window:resize', ['$event'])
    onResize(event: any): void {
        const windowWidth: number = event.target.innerWidth;
        const newDisplayItems: number = windowWidth <= 1526 ? 2 : 3;

        if (newDisplayItems !== this.numberOfItemsToDisplay) {
            this.numberOfItemsToDisplay = newDisplayItems;

            this.friends = this.friendsService
                .getFriendsFeedOptions(this.currentUser.profileId, this.FIRST_PAGE, this.numberOfItemsToDisplay);
        }
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();

        const username: string = this.routingService.getCurrentUsernameForRoute(this.router.url.split("/"));

        this.fetchFriendsAndProfileInfo(username);
    }

    ngOnDestroy(): void {
        this.activateRouteDestroy$.complete();
    }

    editBio(): void {
        this.isEditing = !this.isEditing;

        const bio: string | null = this.bioControl.value;

        if (bio === null || this.profileInfo.profileId !== this.currentUser.profileId) {
            return;
        }
        this.profileService
            .updateProfileBio(this.currentUser.profileId, bio)
            .subscribe(() => {
                this.profileInfo.bio = bio;

                this.bioControl.setValue(this.profileInfo.bio);

                this.matSnackBar.open('Bio updated successfully!', 'Close');
            });
    }

    navigateToFriendProfile(username: string): void {
        window.scrollTo(0, 0);

        this.router
            .navigate([RouterPaths.PROFILE_DIRECT, username, RouterPaths.PROFILE_POSTS])
            .then(() => this.fetchFriendsAndProfileInfo(username));
    }

    private fetchFriendsAndProfileInfo(username: string): void {
        this.profileService
            .getBasicProfileInfoByUsername(username)
            .pipe(takeUntil(this.activateRouteDestroy$))
            .subscribe((profile: Profile) => {
                this.profileInfo = profile;

                this.bioControl = new FormControl(this.profileInfo.bio);

                this.friends = this.friendsService
                    .getFriendsFeedOptions(this.profileInfo.profileId, this.FIRST_PAGE, this.numberOfItemsToDisplay);
            });
    }
}
