import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { MatChipListbox, MatChipOption, MatChipSelectionChange } from "@angular/material/chips";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { AboutOverviewComponent } from "@pages/profile/profile-about/about-overview/about-overview.component";
import {
    AboutWorkEducationComponent
} from "@pages/profile/profile-about/about-work-education/about-work-education.component";
import { AboutLocationsComponent } from "@pages/profile/profile-about/about-locations/about-locations.component";
import { AboutContactComponent } from "@pages/profile/profile-about/about-contact/about-contact.component";
import { NavigationEnd, Router, RouterLink, RouterOutlet } from "@angular/router";
import { RouteOption } from "@interfaces/profile/route-option.interface";
import { RouterPaths } from "@enums/router-paths.enum";
import { filter, Subject, takeUntil } from "rxjs";
import { RoutingService } from "@services/profile/routing.service";

@Component({
    selector: 'app-profile-about',
    standalone: true,
    imports: [
        MatCard,
        MatCardHeader,
        MatCardTitle,
        MatCardContent,
        MatChipListbox,
        MatChipOption,
        MatButton,
        MatIcon,
        AboutOverviewComponent,
        AboutWorkEducationComponent,
        AboutLocationsComponent,
        AboutContactComponent,
        RouterOutlet,
        RouterLink
    ],
    templateUrl: './profile-about.component.html',
    styleUrl: './profile-about.component.scss'
})
export class ProfileAboutComponent implements OnInit, OnDestroy {
    protected readonly options: RouteOption[] = [
        { label: "Overview", route: RouterPaths.PROFILE_ABOUT_OVERVIEW },
        { label: "Work and Education", route: RouterPaths.PROFILE_ABOUT_WORK_EDUCATION },
        { label: "Places You've Lived", route: RouterPaths.PROFILE_ABOUT_LOCATIONS },
        { label: "Contact and Basic Info", route: RouterPaths.PROFILE_ABOUT_CONTACT }
    ];
    protected selectedOption !: RouteOption;
    private routerDestroy$: Subject<void> = new Subject<void>();

    constructor(private routingService: RoutingService) {
    }

    ngOnInit(): void {
        this.selectedOption = this.routingService.getCurrentActivatedRouteOption(this.options);

        this.routingService
            .getUrlSegmentsOnNavigationEnd()
            .pipe(takeUntil(this.routerDestroy$))
            .subscribe((url: string[]) => {
                this.selectedOption = this.routingService.getCurrentActivatedRouteOptionWithUrl(url, this.options);
            });
    }

    ngOnDestroy(): void {
        this.routerDestroy$.complete();
    }

    handleSelectionChange(event: MatChipSelectionChange, option: RouteOption): void {
        if (this.selectedOption === option && !event.selected) {
            event.source.selected = true;
        } else {
            this.selectedOption = option;
        }
    }
}
