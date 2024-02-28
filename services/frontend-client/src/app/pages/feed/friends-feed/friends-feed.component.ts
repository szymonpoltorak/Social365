import { Component } from '@angular/core';
import { MatCard } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { FriendComponent } from "@pages/feed/friends-feed/friend/friend.component";
import { Friend } from "@core/data/feed/Friend";

@Component({
    selector: 'app-friends-feed',
    standalone: true,
    imports: [
        MatCard,
        MatButton,
        MatIcon,
        MatDivider,
        FriendComponent
    ],
    templateUrl: './friends-feed.component.html',
    styleUrl: './friends-feed.component.scss'
})
export class FriendsFeedComponent {
    protected friends: Friend[] = [
        {
            fullName: "Jacek Kowalski",
            isOnline: true
        },
        {
            fullName: "Marek Nowak",
            isOnline: false
        },
        {
            fullName: "Krzysztof Krawczyk",
            isOnline: true
        }
    ];
}
