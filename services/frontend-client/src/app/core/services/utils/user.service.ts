import { Injectable } from '@angular/core';
import { StorageKeys } from "@enums/storage-keys.enum";
import { LocalStorageService } from "@services/utils/local-storage.service";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private localStorage: LocalStorageService) {
    }

    get isUserAuthenticated(): boolean {
        const authToken: string = this.localStorage.getValueFromStorage(StorageKeys.AUTH_TOKEN);

        return authToken !== undefined && authToken !== "";
    }

}
