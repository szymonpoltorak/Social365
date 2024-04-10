import { Component } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatRipple } from "@angular/material/core";
import { RouterOutlet } from "@angular/router";
import { ListSelectOptionComponent } from "@shared/list-select-option/list-select-option.component";

@Component({
    selector: 'app-friends',
    standalone: true,
    imports: [
        ToolbarComponent,
        MatButton,
        MatIcon,
        MatRipple,
        RouterOutlet,
        ListSelectOptionComponent,
    ],
    templateUrl: './friends.component.html',
    styleUrl: './friends.component.scss'
})
export class FriendsComponent {
    options: string[] = ["Friend Requests", "Friend Suggestions"];
    selectedOption: string = "Friend Requests";

    deselectOtherOptions(event: string): void {
        this.selectedOption = event;
    }
}
