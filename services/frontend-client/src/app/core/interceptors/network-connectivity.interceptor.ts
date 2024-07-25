import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from "@angular/core";
import { Router } from "@angular/router";
import { RouterPaths } from "@enums/router-paths.enum";

export const networkConnectivityInterceptor: HttpInterceptorFn = (req, next) => {
    const router: Router = inject(Router);

    if (!window.navigator.onLine) {
        router.navigate([RouterPaths.NETWORK_OFFLINE_DIRECT]);
    }
    return next(req);
};
