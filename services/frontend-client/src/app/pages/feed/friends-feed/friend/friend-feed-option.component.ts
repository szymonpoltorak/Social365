import { Component, Input } from '@angular/core';
import { FriendFeedOption } from "@interfaces/feed/friend-feed-option.interface";
import { MatCard, MatCardAvatar, MatCardHeader, MatCardImage } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { NgOptimizedImage } from "@angular/common";
import { MatRipple } from "@angular/material/core";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";

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
        MatRipple,
        AvatarPhotoComponent
    ],
    templateUrl: './friend-feed-option.component.html',
    styleUrl: './friend-feed-option.component.scss'
})
export class FriendFeedOptionComponent {
    @Input() feedOption !: FriendFeedOption;
}
