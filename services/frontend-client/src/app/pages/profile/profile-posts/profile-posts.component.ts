import { Component, HostListener, OnInit } from '@angular/core';
import { PostsFeedComponent } from "@pages/feed/posts-feed/posts-feed.component";
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatFormField } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { RouterPaths } from "@enums/router-paths.enum";
import { AsyncPipe, NgIf } from "@angular/common";
import { ProfileService } from "@api/profile/profile.service";
import { Profile } from "@interfaces/feed/profile.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { MatSnackBar } from '@angular/material/snack-bar';
import { FriendsService } from '@core/services/api/profile/friends.service';
import { Observable } from "rxjs";
import { Page } from "@interfaces/utils/page.interface";
import { FriendFeedOption } from "@interfaces/feed/friend-feed-option.interface";
import { RoutingService } from '@core/services/profile/routing.service';
import { PostMappings } from "@enums/api/posts-comments/post-mappings.enum";
import { MatProgressSpinner } from "@angular/material/progress-spinner";

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
        AsyncPipe,
        MatProgressSpinner
    ],
    templateUrl: './profile-posts.component.html',
    styleUrl: './profile-posts.component.scss'
})
export class ProfilePostsComponent implements OnInit {
    private readonly FIRST_PAGE: number = 0;
    protected presentedProfile !: Profile;
    protected isEditing: boolean = false;
    protected bioControl !: FormControl<string | null>;
    protected readonly RouterPaths = RouterPaths;
    protected imageUrls: string[] = [
        "https://material.angular.io/assets/img/examples/shiba2.jpg",
        "https://material.angular.io/assets/img/examples/shiba2.jpg",
        "https://material.angular.io/assets/img/examples/shiba2.jpg"
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

        const username: string = this.routingService.getCurrentUsernameForRoute();

        this.fetchFriendsAndProfileInfo(username);
    }

    editBio(): void {
        this.isEditing = !this.isEditing;

        const bio: string | null = this.bioControl.value;

        if (bio === null || this.presentedProfile.profileId !== this.currentUser.profileId) {
            return;
        }
        this.profileService
            .updateProfileBio(this.currentUser.profileId, bio)
            .subscribe(() => {
                this.presentedProfile.bio = bio;

                this.bioControl.setValue(this.presentedProfile.bio);

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
            .subscribe((profile: Profile) => {
                this.presentedProfile = profile;

                this.bioControl = new FormControl(this.presentedProfile.bio);

                this.friends = this.friendsService
                    .getFriendsFeedOptions(this.presentedProfile.profileId, this.FIRST_PAGE, this.numberOfItemsToDisplay);
            });
    }

    protected readonly PostMappings = PostMappings;
}
