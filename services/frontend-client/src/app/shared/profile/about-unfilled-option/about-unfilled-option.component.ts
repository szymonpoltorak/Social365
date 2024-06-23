import { Component, Input } from '@angular/core';
import { MatIcon } from "@angular/material/icon";

@Component({
    selector: 'app-about-unfilled-option',
    standalone: true,
    imports: [
        MatIcon
    ],
    templateUrl: './about-unfilled-option.component.html',
    styleUrl: './about-unfilled-option.component.scss'
})
export class AboutUnfilledOptionComponent {
    @Input() icon !: string;
    @Input() subLabel !: string;
}
