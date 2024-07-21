import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RouterLink, RouterOutlet } from "@angular/router";
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { NgOptimizedImage } from "@angular/common";
import { MatCardModule } from "@angular/material/card";
import { MatDivider } from "@angular/material/divider";
import { MatIcon } from "@angular/material/icon";
import { MatTabsModule } from "@angular/material/tabs";
import { TabOption } from "@interfaces/profile/tab-option.interface";
import { RouterPaths } from "@enums/router-paths.enum";
import { MatButton, MatMiniFabButton } from "@angular/material/button";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { Profile } from "@interfaces/feed/profile.interface";
import { filter, Subject, takeUntil } from "rxjs";
import { RoutingService } from "@services/profile/routing.service";
import { ProfileService } from "@api/profile/profile.service";
import { MatProgressSpinner } from "@angular/material/progress-spinner";

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
        MatProgressSpinner
    ],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit, OnDestroy {
    protected username: string = '';
    protected profileInfo !: Profile;
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
                    .getBasicProfileInfoByUsername(this.username)
                    .subscribe((profile: Profile) => {
                        this.profileInfo = profile;
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
}
