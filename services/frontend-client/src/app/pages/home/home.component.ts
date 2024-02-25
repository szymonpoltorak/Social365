import { Component } from '@angular/core';
import { MatButton, MatFabButton } from "@angular/material/button";
import { MatTooltip } from "@angular/material/tooltip";
import { MatIcon } from "@angular/material/icon";
import { MatLabel } from "@angular/material/form-field";

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [
        MatButton,
        MatFabButton,
        MatTooltip,
        MatIcon
    ],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent {

}
