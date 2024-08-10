import { Component, EventEmitter, Input, Output } from '@angular/core';
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
    @Input() isFriendRequest: boolean = true;
    @Output() addFriend: EventEmitter<FriendElement> = new EventEmitter<FriendElement>();
    @Output() removeFriend: EventEmitter<FriendElement> = new EventEmitter<FriendElement>();

    addOrAcceptFriendRequest(): void {
        this.addFriend.emit(this.friendElement);
    }

    removeOrDeclineFriendRequest(): void {
        this.removeFriend.emit(this.friendElement);
    }

}
