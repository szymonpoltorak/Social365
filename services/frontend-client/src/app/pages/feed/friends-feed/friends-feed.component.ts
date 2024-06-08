import { Component } from '@angular/core';
import { MatCard } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { FriendFeedOptionComponent } from "@pages/feed/friends-feed/friend/friend-feed-option.component";
import { FriendFeedOption } from "@interfaces/feed/friend-feed-option.interface";
import { Observable, of } from "rxjs";
import { AsyncPipe } from "@angular/common";
import { BirthdayComponent } from "@pages/feed/friends-feed/birthday/birthday.component";
import { BirthdayInfo } from "@interfaces/feed/birthday-info.interface";

@Component({
    selector: 'app-friends-feed',
    standalone: true,
    imports: [
        MatCard,
        MatButton,
        MatIcon,
        MatDivider,
        FriendFeedOptionComponent,
        AsyncPipe,
        BirthdayComponent
    ],
    templateUrl: './friends-feed.component.html',
    styleUrl: './friends-feed.component.scss'
})
export class FriendsFeedComponent {
    protected birthdayInfos: BirthdayInfo[] = [
        {
            profile: {
                profileId: "1",
                fullName: "Jacek Kowalski",
                username: "jacek@gmail.com",
                subtitle: "Student at Warsaw University of Technology",
                profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba1.jpg"
            },
            age: 25,
        },
        {
            profile: {
                profileId: "2",
                fullName: "Marek Nowak",
                username: "marek@gmail.com",
                subtitle: "Web developer at Google",
                profilePictureUrl: "https://material.angular.io/assets/img/examples/shiba1.jpg"
            },
            age: 30,
        }
    ];

    protected friends: Observable<FriendFeedOption[]> = of([
        {
            profileId: "1",
            fullName: "Jacek Kowalski",
            isOnline: true,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            profileId: "2",
            fullName: "Marek Nowak",
            isOnline: false,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        },
        {
            profileId: "3",
            fullName: "Krzysztof Krawczyk",
            isOnline: true,
            imageLink: "https://material.angular.io/assets/img/examples/shiba1.jpg"
        }
    ]);

    protected chats: Observable<FriendFeedOption[]> = of([
        {
            profileId: "1",
            fullName: "My Friends",
            isOnline: true,
            imageLink: "https://static.scientificamerican.com/sciam/cache/file/8F2611FB-1329-445F-9428B91317BC067B_source.jpg?w=1200"
        },
        {
            profileId: "2",
            fullName: "Family",
            isOnline: false,
            imageLink: "https://static.scientificamerican.com/sciam/cache/file/8F2611FB-1329-445F-9428B91317BC067B_source.jpg?w=1200"
        },
        {
            profileId: "3",
            fullName: "Students of WUT",
            isOnline: true,
            imageLink: "https://static.scientificamerican.com/sciam/cache/file/8F2611FB-1329-445F-9428B91317BC067B_source.jpg?w=1200"
        }
    ]);
}
