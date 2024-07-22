import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterOutlet } from "@angular/router";
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { NgOptimizedImage } from "@angular/common";
import { MatCardModule } from "@angular/material/card";
import { MatDivider } from "@angular/material/divider";
import { MatIcon } from "@angular/material/icon";
import { MatTabsModule } from "@angular/material/tabs";
import { TabOption } from "@interfaces/profile/tab-option.interface";
import { RouterPaths } from "@enums/router-paths.enum";
import { MatButton, MatIconButton, MatMiniFabButton } from "@angular/material/button";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { Profile } from "@interfaces/feed/profile.interface";
import { Subject, takeUntil } from "rxjs";
import { RoutingService } from "@services/profile/routing.service";
import { ProfileService } from "@api/profile/profile.service";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { FileService } from "@services/utils/file.service";
import { Optional } from "@core/types/profile/optional.type";
import { ImagesService } from "@api/images/images.service";
import { Image } from "@interfaces/images/image.interface";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { ProfileBasicInfo } from "@interfaces/profile/profile-basic-info.interface";

@Component({
    selector: 'app-profile',
    standalone: true,
    imports: [
        ToolbarComponent,
        NgOptimizedImage,
        MatCardModule,
        MatDivider,
        MatIcon,
        MatTabsModule,
        RouterOutlet,
        RouterLink,
        MatButton,
        MatMiniFabButton,
        MatProgressSpinner,
        MatIconButton,
        MatMenu,
        MatMenuItem,
        MatMenuTrigger
    ],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit, OnDestroy {
    protected username: string = '';
    protected profileInfo !: ProfileBasicInfo;
    protected options: TabOption[] = [
        { label: 'Posts', icon: 'lists', route: RouterPaths.PROFILE_POSTS },
        { label: 'About', icon: 'info', route: RouterPaths.PROFILE_ABOUT_MAIN },
        { label: 'Friends', icon: 'people', route: RouterPaths.FRIENDS_PATH },
        { label: 'Photos', icon: 'image', route: RouterPaths.PROFILE_PHOTOS }
    ];
    protected activeRoute: TabOption = this.options[0];
    protected currentUser !: Profile;
    private routerDestroy$: Subject<void> = new Subject<void>();
    private activateRouteDestroy$: Subject<void> = new Subject<void>();

    constructor(private localStorage: LocalStorageService,
                private routingService: RoutingService,
                private profileService: ProfileService,
                private imageService: ImagesService,
                protected fileService: FileService,
                private activatedRoute: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();

        this.activatedRoute
            .paramMap
            .pipe(takeUntil(this.activateRouteDestroy$))
            .subscribe((params) => {
                this.username = params.get("username") as string;

                this.profileService
                    .getBasicProfileInfoByUsername(this.username, this.currentUser.profileId)
                    .subscribe((profile: ProfileBasicInfo) => {
                        this.profileInfo = profile;
                        this.profileInfo.isFriend = true;
                        this.profileInfo.isFollowed = false;
                    });
            })

        this.activeRoute = this.routingService.getCurrentActivatedRouteOption(this.options);

        this.routingService
            .getUrlSegmentsOnNavigationEnd()
            .pipe(takeUntil(this.routerDestroy$))
            .subscribe((url: string[]) => {
                this.activeRoute = this.routingService.getCurrentActivatedRouteOptionWithUrl(url, this.options);
            });
    }

    ngOnDestroy(): void {
        this.routerDestroy$.complete();
        this.activateRouteDestroy$.complete();
    }

    addBannerToProfile(event: any): void {
        const profileBanner: Optional<AttachImage> = this.fileService.processAttachSingleFileEvent(event);

        if (profileBanner === null) {
            return;
        }
        if (this.profileInfo.profileBannerUrl === "") {
            this.imageService
                .uploadImage(this.profileInfo.username, profileBanner)
                .subscribe((image: Image) => {
                    this.profileInfo.profileBannerUrl = image.imagePath;

                    this.profileService
                        .updateProfileBanner(this.profileInfo.username, image.imageId)
                        .subscribe();
                });
        } else {
            this.imageService
                .updateImage(this.profileInfo.profileBannerUrl, profileBanner)
                .subscribe((image: Image) => {
                    this.profileInfo.profileBannerUrl = image.imagePath;

                    this.profileService
                        .updateProfileBanner(this.profileInfo.username, image.imageId)
                        .subscribe();
                });
        }
    }

    changeProfilePicture(event: any): void {
        const profilePicture: Optional<AttachImage> = this.fileService.processAttachSingleFileEvent(event);

        if (profilePicture === null) {
            return;
        }
        this.imageService
            .updateImage(this.profileInfo.username, profilePicture)
            .subscribe((image: Image) => {
                this.profileInfo.profileBannerUrl = image.imagePath;

                this.profileService
                    .updateProfilePicture(this.profileInfo.username, image.imageId)
                    .subscribe();
            });
    }
}
