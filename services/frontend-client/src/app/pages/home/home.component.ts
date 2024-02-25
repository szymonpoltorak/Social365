import { Component } from '@angular/core';
import { MatButton, MatFabButton } from "@angular/material/button";
import { MatTooltip } from "@angular/material/tooltip";

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [
        MatButton,
        MatFabButton,
        MatTooltip
    ],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent {

}
