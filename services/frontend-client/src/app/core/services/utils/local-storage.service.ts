import { Injectable } from '@angular/core';
import { Profile } from "@interfaces/feed/profile.interface";

@Injectable({
    providedIn: 'root'
})
export class LocalStorageService {
    getUserProfileFromStorage(): Profile {
        const user: string | null = localStorage.getItem('currentUser');

        return JSON.parse(user as string);
    }

    getUserProfileIdFromStorage(): string {
        const user: string | null = localStorage.getItem('currentUser');

        if (!user) {
            throw new Error('User not found in local storage');
        }
        return JSON.parse(user).profileId;
    }

    saveUserToStorage(user: Profile): void {
        localStorage.setItem('currentUser', JSON.stringify(user));
    }
}
