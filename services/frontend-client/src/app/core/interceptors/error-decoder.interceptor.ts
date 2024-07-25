import { HttpErrorResponse, HttpEvent, HttpInterceptorFn } from '@angular/common/http';
import { catchError, Observable, throwError } from "rxjs";
import { MatSnackBar } from "@angular/material/snack-bar";
import { inject } from "@angular/core";

export const errorDecoderInterceptor: HttpInterceptorFn = (req, next) => {
    const snackBar: MatSnackBar = inject(MatSnackBar);

    return next(req)
        .pipe(
            catchError((error: HttpErrorResponse) => {
                if (error.status >= 400 && error.status < 500) {
                    snackBar.open(`Client side error has happened! Browser sent wrong data...`, "Understood");
                } else if (error.status >= 500 && error.status < 600) {
                    snackBar.open(`We experienced server side error! That's on us!`, "Understood");
                } else if (error.status < 200 && error.status > 300) {
                    snackBar.open(`App encountered an error!`, "Understood");
                }
                return throwError(() => error);
            })
        ) as Observable<HttpEvent<unknown>>;
};
