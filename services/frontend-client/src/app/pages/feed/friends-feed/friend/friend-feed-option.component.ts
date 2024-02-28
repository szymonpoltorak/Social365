import { Component, Input } from '@angular/core';
import { FriendFeedOption } from "@core/data/feed/FriendFeedOption";
import { MatCard, MatCardAvatar, MatCardHeader, MatCardImage } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { NgOptimizedImage } from "@angular/common";
import { MatRipple } from "@angular/material/core";

@Component({
    selector: 'app-friend-feed-option',
    standalone: true,
    imports: [
        MatCardImage,
        MatCardAvatar,
        MatCard,
        MatCardHeader,
        MatButton,
        NgOptimizedImage,
        MatRipple
    ],
    templateUrl: './friend-feed-option.component.html',
    styleUrl: './friend-feed-option.component.scss'
})
export class FriendFeedOptionComponent {
    @Input() feedOption !: FriendFeedOption;
}
