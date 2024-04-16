import { Component, Input } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatCard, MatCardContent, MatCardHeader, MatCardImage, MatCardTitle } from "@angular/material/card";
import { FriendElement } from "@interfaces/friends/friend-element.interface";

@Component({
    selector: 'app-friend-request',
    standalone: true,
    imports: [
        MatButton,
        MatCard,
        MatCardContent,
        MatCardHeader,
        MatCardImage,
        MatCardTitle
    ],
    templateUrl: './friend-request.component.html',
    styleUrl: './friend-request.component.scss'
})
export class FriendRequestComponent {
    @Input() friendElement !: FriendElement;
}
