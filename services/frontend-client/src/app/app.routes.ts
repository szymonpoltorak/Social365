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
            .then(m => m.ProfileComponent),
        children: [
            {
                path: RouterPaths.PROFILE_POSTS,
                loadComponent: () => import("./pages/profile/profile-posts/profile-posts.component")
                    .then(m => m.ProfilePostsComponent)
            },
            {
                path: RouterPaths.PROFILE_ABOUT,
                loadComponent: () => import("./pages/profile/profile-about/profile-about.component")
                    .then(m => m.ProfileAboutComponent)
            },
            {
                path: RouterPaths.PROFILE_FRIENDS,
                loadComponent: () => import("./pages/profile/profile-friends/profile-friends.component")
                    .then(m => m.ProfileFriendsComponent)
            },
            {
                path: RouterPaths.PROFILE_PHOTOS,
                loadComponent: () => import("./pages/profile/profile-photos/profile-photos.component")
                    .then(m => m.ProfilePhotosComponent)
            }
        ]
    },
    {
        path: RouterPaths.CURRENT_PATH,
        redirectTo: RouterPaths.HOME_DIRECT,
        pathMatch: 'full'
    },
    {
        path: RouterPaths.UNKNOWN_PATH,
        redirectTo: RouterPaths.HOME_DIRECT,
        pathMatch: 'full'
    }
];
