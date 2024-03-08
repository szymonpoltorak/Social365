import { Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";

export const routes: Routes = [
    {
        path: RouterPaths.HOME,
        loadComponent: () => import("./pages/home/home.component")
            .then(m => m.HomeComponent)
    },
    {
        path: RouterPaths.FEED,
        loadComponent: () => import("./pages/feed/feed.component")
            .then(m => m.FeedComponent)
    },
    {
        path: RouterPaths.PROFILE,
        loadComponent: () => import("./pages/profile/profile.component")
            .then(m => m.ProfileComponent)
    },
    {
        path: RouterPaths.CURRENT_PATH,
        redirectTo: RouterPaths.HOME_DIRECT,
        pathMatch: 'full'
    },
    // {
    //     path: RouterPaths.UNKNOWN_PATH,
    //     redirectTo: RouterPaths.HOME_DIRECT,
    //     pathMatch: 'full'
    // }
];
