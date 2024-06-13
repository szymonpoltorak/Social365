import { Injectable } from '@angular/core';
import { Profile } from "@interfaces/feed/profile.interface";

@Injectable({
    providedIn: 'root'
})
export class LocalStorageService {
    getUserProfileFromStorage(): Profile {
        const user: string | null = localStorage.getItem('currentUser');

        if (!user) {
            throw new Error('User not found in local storage');
        }
        return JSON.parse(user);
    }

    saveUserToStorage(user: Profile): void {
        localStorage.setItem('currentUser', JSON.stringify(user));
    }
}
