import { Injectable } from '@angular/core';
import { User } from "@core/data/feed/User";

@Injectable({
    providedIn: 'root'
})
export class LocalStorageService {
    getUserFromStorage(): User {
        const user: string | null = localStorage.getItem('currentUser');

        if (!user) {
            throw new Error('User not found in local storage');
        }
        return JSON.parse(user);
    }

    saveUserToStorage(user: User): void {
        localStorage.setItem('currentUser', JSON.stringify(user));
    }
}
