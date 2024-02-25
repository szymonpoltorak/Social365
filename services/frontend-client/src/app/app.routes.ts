import { Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";

export const routes: Routes = [
    {
        path: RouterPaths.HOME,
        loadComponent: () => import("./pages/home/home.component")
            .then(m => m.HomeComponent)
    },
    {
        path: RouterPaths.CURRENT_PATH,
        redirectTo: RouterPaths.HOME,
        pathMatch: 'full'
    }
];
