import { Component, Input, OnInit } from '@angular/core';
import { AsyncPipe, NgClass } from "@angular/common";
import { MatAutocomplete, MatAutocompleteTrigger, MatOption } from "@angular/material/autocomplete";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { MatMiniFabButton } from "@angular/material/button";
import { MatTooltip } from "@angular/material/tooltip";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { catchError, debounceTime, distinctUntilChanged, EMPTY, Observable, startWith, switchMap } from "rxjs";
import { MatTabsModule } from "@angular/material/tabs";
import { MatBadge } from "@angular/material/badge";
import { RouterPaths } from "@enums/router-paths.enum";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { Profile } from "@interfaces/feed/profile.interface";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { ProfileService } from "@api/profile/profile.service";
import { ProfileQuery } from "@interfaces/feed/profile-query.interface";
import { AuthService } from "@api/auth/auth.service";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";
import { CdkMenu, CdkMenuTrigger } from "@angular/cdk/menu";
import { Notification } from "@interfaces/notifications/notification.interface";
import { MatRipple } from "@angular/material/core";

@Component({
    selector: 'app-toolbar',
    standalone: true,
    imports: [
        AsyncPipe,
        MatAutocomplete,
        MatAutocompleteTrigger,
        MatIcon,
        MatInput,
        MatMenu,
        MatMenuItem,
        MatMiniFabButton,
        MatOption,
        MatTooltip,
        ReactiveFormsModule,
        RouterLink,
        MatMenuTrigger,
        MatTabsModule,
        MatBadge,
        AvatarPhotoComponent,
        FormsModule,
        CdkMenuTrigger,
        CdkMenu,
        NgClass,
        MatRipple
    ],
    templateUrl: './toolbar.component.html',
    styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent implements OnInit {

    @Input() isOnFeed!: boolean;
    protected filteredOptions$ !: Observable<SocialPage<ProfileQuery, PageablePagingState>>;
    protected readonly searchSocialControl: FormControl<string> = new FormControl();
    protected user !: Profile;
    protected readonly RouterPaths = RouterPaths;
    private readonly PAGE_SIZE: number = 5;
    protected notifications: Notification[] = [
        {
            notificationId: "1",
            notificationText: "You have a new message",
            isRead: false,
            authorsProfileImageUrl: "/images/nouser@example.com/shiba1.jpg",
            targetProfileId: "1"
        },
        {
            notificationId: "1",
            notificationText: "Andrzej Kowalski liked your post",
            isRead: true,
            authorsProfileImageUrl: "/images/nouser@example.com/shiba1.jpg",
            targetProfileId: "1"
        },
    ];
    newNotifications: number = this.notifications.filter(notification => !notification.isRead).length;

    constructor(protected router: Router,
                private profileService: ProfileService,
                private authService: AuthService,
                private localStorageService: LocalStorageService) {
    }

    ngOnInit(): void {
        this.filteredOptions$ = this.searchSocialControl.valueChanges.pipe(
            startWith(""),
            distinctUntilChanged(),
            debounceTime(750),
            switchMap((pattern: string) => this.fetchProfiles(pattern))
        );
        this.user = this.localStorageService.getUserProfileFromStorage();
    }

    navigateToSearch(): void {
        this.router.navigate([RouterPaths.SEARCH_PATH_DIRECT], {
            queryParams: {
                pattern: this.searchSocialControl.value
            }
        });
    }

    private fetchProfiles(pattern: string): Observable<SocialPage<ProfileQuery, PageablePagingState>> {
        if (pattern === "") {
            return EMPTY;
        }
        return this.profileService.getProfilesByPattern(pattern, PageablePagingState.firstPage(this.PAGE_SIZE));
    }

    logout(): void {
        this.authService
            .logout()
            .pipe(
                catchError(() => {
                    this.localStorageService.clearStorage();

                    return EMPTY;
                }
            ))
            .subscribe(() => {
                this.localStorageService.clearStorage();

                this.router.navigate([RouterPaths.HOME_DIRECT]);
            });
    }

    readNotification(notification: Notification): void {
        notification.isRead = true;
        this.newNotifications = this.notifications.filter(notification => !notification.isRead).length;
    }
}
