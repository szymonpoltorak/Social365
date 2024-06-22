import { Injectable } from '@angular/core';
import { RouteOption } from "@interfaces/profile/route-option.interface";
import { NavigationEnd, Router } from "@angular/router";
import { filter, map, Observable } from "rxjs";
import { LocalStorageService } from "@services/utils/local-storage.service";

@Injectable({
    providedIn: 'root'
})
export class RoutingService {

    constructor(private router: Router,
                private localStorage: LocalStorageService) {
    }

    getUrlSegmentsOnNavigationEnd(): Observable<string[]> {
        return this.router
            .events
            .pipe(
                filter(event => event instanceof NavigationEnd),
                map(event => (event as NavigationEnd).url.split("/")),
            );
    }

    isCurrentUserAbleToEdit(): boolean {
        return this.getCurrentUsernameForRoute() === this.localStorage.getUserProfileFromStorage().username;
    }

    getCurrentActivatedRouteOption<T extends RouteOption>(options: T[]): T {
        return this.getCurrentActivatedRouteOptionWithUrl<T>(this.router.url.split("/"), options);
    }

    getCurrentActivatedRouteOptionWithUrl<T extends RouteOption>(url: string[], options: T[]): T {
        const currentChildRoute: string = url[url.length - 1];

        let foundRoute: T | undefined = options
            .find((option: T) => option.route === currentChildRoute);

        if (foundRoute === undefined) {
            foundRoute = options
                .find((option: T) => option.label.toLowerCase() === url[url.length - 2]);
        }
        return foundRoute as T;
    }

    getCurrentUsernameForRoute(): string {
        const url: string[] = this.router.url.split("/");

        return url.find((route: string) => route.includes('@'))?.trim() || '';
    }

}
