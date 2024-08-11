import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandlerFn,
    HttpInterceptorFn,
    HttpRequest,
    HttpStatusCode
} from '@angular/common/http';
import { BehaviorSubject, catchError, filter, map, Observable, switchMap, take, throwError } from "rxjs";
import { Auth } from "@interfaces/auth/auth-response.interface";
import { StorageKeys } from "@enums/storage-keys.enum";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { inject } from "@angular/core";
import { AuthService } from "@api/auth/auth.service";
import { Optional } from "@core/types/profile/optional.type";

export const authInterceptor: HttpInterceptorFn = (request: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> => {
    const localStorage: LocalStorageService = inject(LocalStorageService);
    const authService: AuthService = inject(AuthService);
    let isRefreshing: boolean = false;
    const refreshTokenSubject: BehaviorSubject<Optional<string>> = new BehaviorSubject<Optional<string>>(null);

    const addTokenToRequest = (token: string, req: HttpRequest<unknown>): HttpRequest<unknown> => {
        if (token === "") {
            return req;
        }
        return req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });
    };

    const refreshUsersTokenIfPossible = (req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> => {
        if (!isRefreshing) {
            isRefreshing = true;
            refreshTokenSubject.next(null);

            return authService.refreshToken(localStorage.getValueFromStorage(StorageKeys.REFRESH_TOKEN))
                .pipe(
                    switchMap((data: Auth) => {
                        isRefreshing = false;
                        refreshTokenSubject.next(data.token.authToken);

                        return next(addTokenToRequest(data.token.authToken, req));
                    })
                );
        }
        return refreshTokenSubject.pipe(
            filter((token: Optional<string>) => token != null),
            map((token: Optional<string>) => token as string),
            take(1),
            switchMap((authToken: string) => {
                return next(addTokenToRequest(authToken, req));
            })
        );
    };

    const key: StorageKeys = isRefreshing ? StorageKeys.REFRESH_TOKEN : StorageKeys.AUTH_TOKEN;
    const token: string = localStorage.getValueFromStorage(key);

    return next(addTokenToRequest(token, request)).pipe(
        catchError((error: Error) => {
            if (error instanceof HttpErrorResponse && error.status === HttpStatusCode.Forbidden) {
                localStorage.removeValueFromStorage(StorageKeys.AUTH_TOKEN);

                return refreshUsersTokenIfPossible(request, next);
            }
            return throwError(() => error);
        })
    );
};