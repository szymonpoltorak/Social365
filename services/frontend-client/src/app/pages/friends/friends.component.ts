import { Component, OnDestroy, OnInit } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatRipple } from "@angular/material/core";
import { RouterOutlet } from "@angular/router";
import { ListSelectOptionComponent } from "@shared/list-select-option/list-select-option.component";
import { Subject, takeUntil } from "rxjs";
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
export class FriendsComponent implements OnInit, OnDestroy {
    selectedOption !: RouteOption;
    protected options: RouteOption[] = [
        { label: "Friend Requests", route: RouterPaths.FRIEND_REQUESTS },
        { label: "Friend Suggestion", route: RouterPaths.FRIEND_SUGGESTIONS }
    ];
    private routerDestroy$: Subject<void> = new Subject<void>();

    constructor(private routingService: RoutingService) {
    }

    ngOnDestroy(): void {
        this.routerDestroy$.complete();
    }

    ngOnInit(): void {
        this.selectedOption = this.routingService.getCurrentActivatedRouteOption(this.options);

        this.routingService
            .getUrlSegmentsOnNavigationEnd()
            .pipe(takeUntil(this.routerDestroy$))
            .subscribe(url => {
                this.selectedOption = this.routingService.getCurrentActivatedRouteOptionWithUrl(url, this.options);
            });
    }

    deselectOtherOptions(event: RouteOption): void {
        this.selectedOption = this.options.filter(option => option === event)[0];
    }
}
