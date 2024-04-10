import { Component, OnInit } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatListOption, MatSelectionList } from "@angular/material/list";
import { MatRipple } from "@angular/material/core";
import { RouterOutlet } from "@angular/router";

@Component({
    selector: 'app-friends',
    standalone: true,
    imports: [
        ToolbarComponent,
        MatButton,
        MatIcon,
        MatSelectionList,
        MatListOption,
        MatRipple,
        RouterOutlet,
    ],
    templateUrl: './friends.component.html',
    styleUrl: './friends.component.scss'
})
export class FriendsComponent implements OnInit {
    ngOnInit(): void {
        console.log('FriendsComponent initialized');
    }

}
