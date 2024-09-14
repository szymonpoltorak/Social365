import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { AsyncPipe, NgClass } from "@angular/common";
import { MatAutocomplete, MatAutocompleteTrigger, MatOption } from "@angular/material/autocomplete";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { MatButton, MatMiniFabButton } from "@angular/material/button";
import { MatTooltip } from "@angular/material/tooltip";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import {
    catchError,
    debounceTime,
    distinctUntilChanged,
    EMPTY,
    Observable,
    startWith,
    Subject,
    Subscription,
    switchMap,
    takeUntil
} from "rxjs";
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
import { NotificationsGatewayService } from "@api/notifications/notifications-gateway.service";
import { NotificationsService } from "@api/notifications/notifications.service";

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
        MatRipple,
        MatButton
    ],
    templateUrl: './toolbar.component.html',
    styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent implements OnInit, OnDestroy {

    @Input() isOnFeed!: boolean;
    private notificationsSubscription$ !: Subscription;
    private notificationsDestroy$: Subject<void> = new Subject<void>();
    protected filteredOptions$ !: Observable<SocialPage<ProfileQuery, PageablePagingState>>;
    protected readonly searchSocialControl: FormControl<string> = new FormControl();
    protected user !: Profile;
    protected readonly RouterPaths = RouterPaths;
    private readonly PAGE_SIZE: number = 4;
    protected notifications !: SocialPage<Notification, PageablePagingState>;
    protected newNotifications !: number;

    constructor(protected router: Router,
                private profileService: ProfileService,
                private authService: AuthService,
                private notificationsService: NotificationsService,
                private notificationsGatewayService: NotificationsGatewayService,
                private localStorageService: LocalStorageService) {
    }

    ngOnInit(): void {
        this.notificationsService
            .getNotificationsForUser(PageablePagingState.firstPage(this.PAGE_SIZE))
            .subscribe((notifications: SocialPage<Notification, PageablePagingState>) => {
                this.notifications = notifications;
                this.newNotifications = this.notifications.filter(notification => !notification.isRead).length;
            });

        this.notificationsSubscription$ = this.notificationsGatewayService.connect().subscribe({
            next: (notification: Notification) => {
                if (this.notifications.contains(notification)) {
                    return;
                }
                this.notifications.add(notification);
                this.newNotifications = this.notifications.filter(notification => !notification.isRead).length;
            },
            error: (err) => console.error('WebSocket error:', err),
            complete: () => console.log('WebSocket connection closed'),
        });

        this.filteredOptions$ = this.searchSocialControl.valueChanges.pipe(
            startWith(""),
            distinctUntilChanged(),
            debounceTime(750),
            switchMap((pattern: string) => this.fetchProfiles(pattern)),
            takeUntil(this.notificationsDestroy$)
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

    ngOnDestroy(): void {
        if (this.notificationsSubscription$) {
            this.notificationsSubscription$.unsubscribe();
        }
        this.notificationsGatewayService.disconnect();
        this.notificationsDestroy$.next();
        this.notificationsDestroy$.complete();
    }

    readNotifications(): void {
        this.notificationsService
            .readNotifications()
            .subscribe(() => this.newNotifications = 0);
    }

    loadMoreNotifications(): void {
        this.notificationsService
            .getNotificationsForUser(this.notifications.nextPagingState())
            .subscribe((notifications: SocialPage<Notification, PageablePagingState>) => {
                this.notifications.concatAndUpdate(notifications);
            });
    }
}
