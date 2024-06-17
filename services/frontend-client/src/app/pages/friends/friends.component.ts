import { Component, OnInit } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatRipple } from "@angular/material/core";
import { NavigationEnd, Router, RouterOutlet } from "@angular/router";
import { ListSelectOptionComponent } from "@shared/list-select-option/list-select-option.component";
import { filter, Subject, takeUntil } from "rxjs";
import { RoutingService } from "@services/profile/routing.service";
import { RouteOption } from "@interfaces/profile/route-option.interface";
import { RouterPaths } from "@enums/router-paths.enum";

@Component({
    selector: 'app-friends',
    standalone: true,
    imports: [
        ToolbarComponent,
        MatButton,
        MatIcon,
        MatRipple,
        RouterOutlet,
        ListSelectOptionComponent,
    ],
    templateUrl: './friends.component.html',
    styleUrl: './friends.component.scss'
})
export class FriendsComponent implements OnInit {
    private routerDestroy$: Subject<void> = new Subject<void>();
    options: RouteOption[] = [
        { label: "Friend Requests", route: RouterPaths.FRIEND_REQUESTS },
        { label: "Friend Suggestions", route: RouterPaths.FRIEND_SUGGESTIONS }
    ];
    selectedOption !: RouteOption;

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

    deselectOtherOptions(event: RouteOption): void {
        this.selectedOption = this.options.filter(option => option === event)[0];
    }
}
