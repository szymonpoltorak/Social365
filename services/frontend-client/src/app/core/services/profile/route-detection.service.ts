import { Injectable } from '@angular/core';
import { RouteOption } from "@interfaces/profile/route-option.interface";

@Injectable({
    providedIn: 'root'
})
export class RouteDetectionService {
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
}