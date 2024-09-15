import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatTooltip } from "@angular/material/tooltip";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { FriendElement } from "@interfaces/friends/friend-element.interface";

@Component({
    selector: 'app-friend-profile',
    standalone: true,
    imports: [
        MatIconButton,
        MatIcon,
        MatTooltip,
        MatMenuTrigger,
        MatMenu,
        MatMenuItem
    ],
    templateUrl: './friend-profile.component.html',
    styleUrl: './friend-profile.component.scss'
})
export class FriendProfileComponent {

    @Input() friend !: FriendElement;
    @Output() navigateToProfile: EventEmitter<string> = new EventEmitter<string>();

}
