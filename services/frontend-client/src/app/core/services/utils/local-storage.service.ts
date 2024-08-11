import { Injectable } from '@angular/core';
import { Profile } from "@interfaces/feed/profile.interface";
import { Optional } from "@core/types/profile/optional.type";
import { StorageKeys } from "@enums/storage-keys.enum";
import { Token } from "@interfaces/auth/token.interface";

@Injectable({
    providedIn: 'root'
})
export class LocalStorageService {

    getUserProfileFromStorage(): Profile {
        const user: string = this.getValueFromStorage(StorageKeys.CURRENT_USER);

        return JSON.parse(user);
    }

    getUserProfileIdFromStorage(): string {
        const user: Profile = this.getUserProfileFromStorage();

        return user.profileId;
    }

    saveUserToStorage(user: Profile): void {
        localStorage.setItem(StorageKeys.CURRENT_USER, JSON.stringify(user));
    }

    removeValueFromStorage(key: StorageKeys): void {
        localStorage.removeItem(key);
    }

    getValueFromStorage(key: StorageKeys): string {
        const value: Optional<string> = localStorage.getItem(key);

        return value == null ? "" : value;
    }

    saveTokenToStorage(token: Token): void {
        localStorage.setItem(StorageKeys.AUTH_TOKEN, token.authToken);

        localStorage.setItem(StorageKeys.REFRESH_TOKEN, token.refreshToken);
    }

    clearStorage(): void {
        localStorage.clear();
    }
}
