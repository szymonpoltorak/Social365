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

    constructor(private router: Router,
                private routeDetectionService: RoutingService) {
    }

    ngOnInit(): void {
        this.selectedOption = this.routeDetectionService
            .getCurrentActivatedRouteOption(this.router.url.split("/"), this.options);

        this.router
            .events
            .pipe(
                filter(event => event instanceof NavigationEnd),
                takeUntil(this.routerDestroy$)
            )
            .subscribe(event => {
                const newEvent: NavigationEnd = event as NavigationEnd;
                const url: string[] = newEvent.url.split("/");

                this.selectedOption = this.routeDetectionService.getCurrentActivatedRouteOption(url, this.options);
            });
    }

    ngOnDestroy(): void {
        this.routerDestroy$.complete();
    }

    handleSelectionChange(event: MatChipSelectionChange, option: RouteOption) : void{
        if (this.selectedOption === option && !event.selected) {
            event.source.selected = true;
        } else {
            this.selectedOption = option;
        }
    }
}
