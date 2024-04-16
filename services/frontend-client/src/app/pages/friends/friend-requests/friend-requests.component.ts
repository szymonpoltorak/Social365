import { Component } from '@angular/core';
import { MatCardModule } from "@angular/material/card";
import { MatButton } from "@angular/material/button";

@Component({
    selector: 'app-friend-requests',
    standalone: true,
    imports: [
        MatCardModule,
        MatButton
    ],
    templateUrl: './friend-requests.component.html',
    styleUrl: './friend-requests.component.scss'
})
export class FriendRequestsComponent {

}
