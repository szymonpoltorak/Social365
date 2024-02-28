import { Component } from '@angular/core';
import { MatCard } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { FriendFeedOptionComponent } from "@pages/feed/friends-feed/friend/friend-feed-option.component";
import { FriendFeedOption } from "@core/data/feed/FriendFeedOption";
import { Observable, of } from "rxjs";
import { AsyncPipe } from "@angular/common";

@Component({
    selector: 'app-friends-feed',
    standalone: true,
    imports: [
        MatCard,
        MatButton,
        MatIcon,
        MatDivider,
        FriendFeedOptionComponent,
        AsyncPipe
    ],
    templateUrl: './friends-feed.component.html',
    styleUrl: './friends-feed.component.scss'
})
export class FriendsFeedComponent {
    protected friends: Observable<FriendFeedOption[]> = of([
        {
            fullName: "Jacek Kowalski",
            isOnline: true,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            fullName: "Marek Nowak",
            isOnline: false,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            fullName: "Krzysztof Krawczyk",
            isOnline: true,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        }
    ]);
    protected chats: Observable<FriendFeedOption[]> = of([
        {
            fullName: "My Friends",
            isOnline: true,
            imageLink: "https://static.scientificamerican.com/sciam/cache/file/8F2611FB-1329-445F-9428B91317BC067B_source.jpg?w=1200"
        },
        {
            fullName: "Family",
            isOnline: false,
            imageLink: "https://static.scientificamerican.com/sciam/cache/file/8F2611FB-1329-445F-9428B91317BC067B_source.jpg?w=1200"
        },
        {
            fullName: "Students of WUT",
            isOnline: true,
            imageLink: "https://static.scientificamerican.com/sciam/cache/file/8F2611FB-1329-445F-9428B91317BC067B_source.jpg?w=1200"
        }
    ]);
}
