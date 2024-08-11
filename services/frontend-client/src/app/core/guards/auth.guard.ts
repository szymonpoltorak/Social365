import { CanActivateFn, Router } from '@angular/router';
import { inject } from "@angular/core";
import { UserService } from "@services/utils/user.service";
import { RouterPaths } from "@enums/router-paths.enum";
import { LocalStorageService } from "@services/utils/local-storage.service";

export const authGuard: CanActivateFn = (route, state) => {
    const userService: UserService = inject(UserService);
    const router: Router = inject(Router);
    const localStorage: LocalStorageService = inject(LocalStorageService);

    if (!userService.isUserAuthenticated) {
        localStorage.clearStorage();

        return router.createUrlTree([RouterPaths.HOME_DIRECT]);
    }
    return true;
};
