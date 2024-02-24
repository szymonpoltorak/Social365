import { Component } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatCard, MatCardHeader } from "@angular/material/card";

@Component({
  selector: 'app-home',
  standalone: true,
    imports: [
        MatButton,
        MatCard,
        MatCardHeader
    ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
