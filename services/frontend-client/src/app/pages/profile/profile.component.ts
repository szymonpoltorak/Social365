import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RouterLink, RouterOutlet } from "@angular/router";
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { NgOptimizedImage } from "@angular/common";
import { Profile } from "@core/data/feed/Profile";
import { MatCardModule } from "@angular/material/card";
import { MatDivider } from "@angular/material/divider";
import { MatIcon } from "@angular/material/icon";
import { MatTabsModule } from "@angular/material/tabs";
import { TabOption } from "@core/data/profile/TabOption";
import { RouterPaths } from "@enums/RouterPaths";
import { MatButton } from "@angular/material/button";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { User } from "@core/data/feed/User";
import { filter, Subject, takeUntil } from "rxjs";

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
        MatButton
    ],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit, OnDestroy {
    protected username: string = '';
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
    protected options: TabOption[] = [
        { label: 'Posts', icon: 'lists', route: RouterPaths.PROFILE_POSTS },
        { label: 'About', icon: 'info', route: RouterPaths.PROFILE_ABOUT_MAIN },
        { label: 'Friends', icon: 'people', route: RouterPaths.PROFILE_FRIENDS },
        { label: 'Photos', icon: 'image', route: RouterPaths.PROFILE_PHOTOS }
    ];
    protected activeRoute: TabOption = this.options[0];
    protected currentUser !: User;
    private routerDestroy$: Subject<void> = new Subject<void>();

    constructor(private activatedRoute: ActivatedRoute,
                private localStorage: LocalStorageService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.username = this.activatedRoute.snapshot.params['username'];
        this.currentUser = this.localStorage.getUserFromStorage();

        this.initActivatedRoute(this.router.url.split("/"));

        this.router
            .events
            .pipe(
                filter(event => event instanceof NavigationEnd),
                takeUntil(this.routerDestroy$)
            )
            .subscribe(event => {
                const newEvent: NavigationEnd = event as NavigationEnd;
                const url: string[] = newEvent.url.split("/");

                this.initActivatedRoute(url);
            });
    }

    private initActivatedRoute(url: string[]): void {
        const currentChildRoute: string = url[url.length - 1];

        let foundRoute: TabOption | undefined = this.options
            .find((option: TabOption) => option.route === currentChildRoute);

        if (foundRoute === undefined) {
            foundRoute = this.options
                .find((option: TabOption) => option.label.toLowerCase() === url[url.length - 2]);
        }

        if (!foundRoute) {
            throw new Error('Invalid route!');
        }
        this.activeRoute = foundRoute;
    }

    ngOnDestroy(): void {
        this.routerDestroy$.next();
        this.routerDestroy$.complete();
    }
}
