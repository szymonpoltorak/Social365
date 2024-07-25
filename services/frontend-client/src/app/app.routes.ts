import { Routes } from '@angular/router';
import { RouterPaths } from "@enums/router-paths.enum";

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
                    .then(m => m.ProfileAboutComponent),
                children: [
                    {
                        path: RouterPaths.PROFILE_ABOUT_OVERVIEW,
                        loadComponent: () => import("./pages/profile/profile-about/about-overview/about-overview.component")
                            .then(m => m.AboutOverviewComponent)
                    },
                    {
                        path: RouterPaths.PROFILE_ABOUT_WORK_EDUCATION,
                        loadComponent: () => import("./pages/profile/profile-about/about-work-education/about-work-education.component")
                            .then(m => m.AboutWorkEducationComponent)
                    },
                    {
                        path: RouterPaths.PROFILE_ABOUT_LOCATIONS,
                        loadComponent: () => import("./pages/profile/profile-about/about-locations/about-locations.component")
                            .then(m => m.AboutLocationsComponent)
                    },
                    {
                        path: RouterPaths.PROFILE_ABOUT_CONTACT,
                        loadComponent: () => import("./pages/profile/profile-about/about-contact/about-contact.component")
                            .then(m => m.AboutContactComponent)
                    }
                ]
            },
            {
                path: RouterPaths.FRIENDS_PATH,
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
        path: RouterPaths.LOGIN_PATH,
        loadComponent: () => import("@pages/auth/login/login.component")
            .then(m => m.LoginComponent),
    },
    {
        path: RouterPaths.REGISTER_PATH,
        loadComponent: () => import("@pages/auth/register/register.component")
            .then(m => m.RegisterComponent),
    },
    {
        path: RouterPaths.FRIENDS_PATH,
        loadComponent: () => import("./pages/friends/friends.component")
            .then(m => m.FriendsComponent),
        children: [
            {
                path: RouterPaths.FRIEND_REQUESTS,
                loadComponent: () => import("./pages/friends/friend-requests/friend-requests.component")
                    .then(m => m.FriendRequestsComponent)
            },
            {
                path: RouterPaths.FRIEND_SUGGESTIONS,
                loadComponent: () => import("./pages/friends/friend-suggestions/friend-suggestions.component")
                    .then(m => m.FriendSuggestionsComponent)
            }
        ]
    },
    {
        path: RouterPaths.SEARCH_PATH,
        loadComponent: () => import("./pages/search/search.component")
            .then(m => m.SearchComponent)
    },
    {
        path: RouterPaths.NETWORK_OFFLINE,
        loadComponent: () => import("./pages/network-offline/network-offline.component")
            .then(m => m.NetworkOfflineComponent)
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
