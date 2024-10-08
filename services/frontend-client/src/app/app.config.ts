import { ApplicationConfig, isDevMode } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideServiceWorker } from '@angular/service-worker';
import { provideNativeDateAdapter } from "@angular/material/core";
import { provideHttpClient, withInterceptors } from "@angular/common/http";
import { networkConnectivityInterceptor } from "@core/interceptors/network-connectivity.interceptor";
import { errorDecoderInterceptor } from "@core/interceptors/error-decoder.interceptor";
import { authInterceptor } from "@core/interceptors/auth.interceptor";

export const appConfig: ApplicationConfig = {
    providers: [
        provideRouter(routes),
        provideAnimationsAsync(),
        provideServiceWorker('ngsw-worker.js', {
            enabled: !isDevMode(),
            registrationStrategy: 'registerWhenStable:30000'
        }),
        provideNativeDateAdapter(),
        provideHttpClient(withInterceptors([
            networkConnectivityInterceptor,
            errorDecoderInterceptor,
            authInterceptor
        ]))
    ]
};
