import { Component } from '@angular/core';
import { MatFabButton } from "@angular/material/button";
import { Router } from "@angular/router";
import { RouterPaths } from "@enums/router-paths.enum";
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
    selector: 'app-network-offline',
    standalone: true,
    imports: [
        MatFabButton
    ],
    templateUrl: './network-offline.component.html',
    styleUrl: './network-offline.component.scss'
})
export class NetworkOfflineComponent {

    constructor(private router: Router,
                private snackBar: MatSnackBar) {
    }

    returnToFeed(): void {
        if (window.navigator.onLine) {
            this.router.navigate([RouterPaths.FEED_DIRECT]);

            return;
        }
        this.snackBar.open('You are still offline', 'Close', {
            duration: 2000
        });
    }
}
