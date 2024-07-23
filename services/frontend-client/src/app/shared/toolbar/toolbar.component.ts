import { Component, Input, OnInit } from '@angular/core';
import { AsyncPipe } from "@angular/common";
import { MatAutocomplete, MatAutocompleteTrigger, MatOption } from "@angular/material/autocomplete";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { MatMiniFabButton } from "@angular/material/button";
import { MatTooltip } from "@angular/material/tooltip";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { debounceTime, distinctUntilChanged, EMPTY, Observable, startWith, switchMap } from "rxjs";
import { MatTabsModule } from "@angular/material/tabs";
import { MatBadge } from "@angular/material/badge";
import { RouterPaths } from "@enums/router-paths.enum";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { Profile } from "@interfaces/feed/profile.interface";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { ProfileService } from "@api/profile/profile.service";
import { ProfileQuery } from "@interfaces/feed/profile-query.interface";
import { Page } from "@interfaces/utils/page.interface";

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
        FormsModule
    ],
    templateUrl: './toolbar.component.html',
    styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent implements OnInit {
    @Input() isOnFeed!: boolean;
    filteredOptions$ !: Observable<Page<ProfileQuery>>;
    newMessages: number = 5;
    newNotifications: number = 0;
    protected readonly searchSocialControl: FormControl<string> = new FormControl();
    protected user !: Profile;
    protected readonly RouterPaths = RouterPaths;
    private readonly FIRST_PAGE: number = 0;
    private readonly PAGE_SIZE: number = 5;

    constructor(protected router: Router,
                private profileService: ProfileService,
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

    private fetchProfiles(pattern: string): Observable<Page<ProfileQuery>> {
        if (pattern === "") {
            return EMPTY;
        }
        return this.profileService.getProfilesByPattern(pattern, this.FIRST_PAGE, this.PAGE_SIZE);
    }
}
