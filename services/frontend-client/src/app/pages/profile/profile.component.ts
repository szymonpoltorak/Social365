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
        MatMiniFabButton
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

    constructor(private localStorage: LocalStorageService,
                private routingService: RoutingService,
                private profileService: ProfileService,
                private activatedRoute: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit(): void {
        this.username = this.activatedRoute.snapshot.params["username"];
        this.currentUser = this.localStorage.getUserProfileFromStorage();

        this.activeRoute = this.routingService
            .getCurrentActivatedRouteOption(this.router.url.split("/"), this.options);

        this.profileService
            .getBasicProfileInfoByUsername(this.username)
            .subscribe((profile: Profile) => {
                this.profileInfo = profile;
            });

        this.router
            .events
            .pipe(
                filter(event => event instanceof NavigationEnd),
                takeUntil(this.routerDestroy$)
            )
            .subscribe(event => {
                const newEvent: NavigationEnd = event as NavigationEnd;
                const url: string[] = newEvent.url.split("/");

                this.activeRoute = this.routingService.getCurrentActivatedRouteOption(url, this.options);
            });
    }

    ngOnDestroy(): void {
        this.routerDestroy$.complete();
    }
}
