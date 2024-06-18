import { Injectable } from '@angular/core';
import { RouteOption } from "@interfaces/profile/route-option.interface";
import { ActivatedRoute } from "@angular/router";
import { ActivatedRouteKey } from "@enums/profile/activated-route-key.enum";

@Injectable({
    providedIn: 'root'
})
export class RoutingService {

    constructor(private activatedRoute: ActivatedRoute) {
    }

    getCurrentActivatedRouteOption<T extends RouteOption>(url: string[], options: T[]): T {
        const currentChildRoute: string = url[url.length - 1];

        let foundRoute: T | undefined = options
            .find((option: T) => option.route === currentChildRoute);

        if (foundRoute === undefined) {
            foundRoute = options
                .find((option: T) => option.label.toLowerCase() === url[url.length - 2]);
        }

        if (!foundRoute) {
            throw new Error('Invalid route!');
        }
        return foundRoute;
    }

    getActivatedRouteParam(username: ActivatedRouteKey): string {
        return this.activatedRoute.snapshot.params[username];
    }
}
