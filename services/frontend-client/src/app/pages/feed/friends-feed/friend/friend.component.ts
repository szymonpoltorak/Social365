import { Component, Input } from '@angular/core';
import { Friend } from "@core/data/feed/Friend";
import { MatCard, MatCardAvatar, MatCardHeader, MatCardImage } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { NgOptimizedImage } from "@angular/common";
import { MatRipple } from "@angular/material/core";

@Component({
    selector: 'app-friend',
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
    templateUrl: './friend.component.html',
    styleUrl: './friend.component.scss'
})
export class FriendComponent {
    @Input() friend !: Friend;
}
