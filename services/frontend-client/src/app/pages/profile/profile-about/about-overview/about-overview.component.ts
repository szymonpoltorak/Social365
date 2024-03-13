import { Component } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatCardTitle } from "@angular/material/card";
import { MatIcon } from "@angular/material/icon";

@Component({
  selector: 'app-about-overview',
  standalone: true,
    imports: [
        MatButton,
        MatCardTitle,
        MatIcon
    ],
  templateUrl: './about-overview.component.html',
  styleUrl: './about-overview.component.scss'
})
export class AboutOverviewComponent {

}
