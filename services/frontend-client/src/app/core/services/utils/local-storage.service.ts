import { Injectable } from '@angular/core';
import { Profile } from "@interfaces/feed/profile.interface";
import { Optional } from "@core/types/profile/optional.type";
import { StorageKeys } from "@enums/storage-keys.enum";

@Injectable({
    providedIn: 'root'
})
export class LocalStorageService {

    private currentUser: Optional<Profile> = null;

    getUserProfileFromStorage(): Profile {
        if (this.currentUser !== null) {
            return this.currentUser;
        }
        const user: string | null = localStorage.getItem('currentUser');

        return JSON.parse(user as string);
    }

    getUserProfileIdFromStorage(): string {
        if (this.currentUser !== null) {
            return this.currentUser.profileId;
        }
        const user: string | null = localStorage.getItem('currentUser');

        if (!user) {
            throw new Error('User not found in local storage');
        }
        return JSON.parse(user).profileId;
    }

    saveUserToStorage(user: Profile): void {
        localStorage.setItem('currentUser', JSON.stringify(user));
    }

    removeValueFromStorage(key: StorageKeys): void {
        localStorage.removeItem(key);
    }

    getValueFromStorage(key: StorageKeys): string {
        const value: Optional<string> = localStorage.getItem(key);

        return value == null ? "" : value;
    }

}
