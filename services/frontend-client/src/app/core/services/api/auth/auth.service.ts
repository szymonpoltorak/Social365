import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, take } from "rxjs";
import { RegisterRequest } from "@interfaces/auth/register-request.interface";
import { Auth } from "@interfaces/auth/auth-response.interface";
import { LoginRequest } from "@interfaces/auth/login-request.interface";
import { AuthMappings } from "@enums/api/auth/auth-mappings.enum";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(private http: HttpClient) {
    }

    registerUser(registerRequest: RegisterRequest): Observable<Auth> {
        return this.http.post<Auth>(AuthMappings.REGISTER_MAPPING, registerRequest).pipe(take(1));
    }

    loginUser(loginRequest: LoginRequest): Observable<Auth> {
        return this.http.post<Auth>(AuthMappings.LOGIN_MAPPING, loginRequest).pipe(take(1));
    }

    refreshToken(refreshToken: string): Observable<Auth> {
        return this.http.post<Auth>(AuthMappings.REFRESH_MAPPING, {}, {
            params: {
                refreshToken: refreshToken
            }
        }).pipe(take(1));
    }

    logout(): Observable<void> {
        return this.http.post<void>(AuthMappings.LOGOUT_MAPPING, {}).pipe(take(1));
    }
}
