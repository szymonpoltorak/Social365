import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatIcon } from "@angular/material/icon";
import { MatIconButton } from "@angular/material/button";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { AboutOptionData } from "@core/data/profile/AboutOptionData";
import { PrivacyLevel } from "@enums/profile/PrivacyLevel";

@Component({
    selector: 'app-about-option',
    standalone: true,
    imports: [
        MatIcon,
        MatIconButton,
        MatMenu,
        MatMenuItem,
        MatMenuTrigger
    ],
    templateUrl: './about-option.component.html',
    styleUrl: './about-option.component.scss'
})
export class AboutOptionComponent {
    @Input() option !: AboutOptionData;
    @Input() label !: string;
    @Input() icon !: string;
    @Input() subLabel !: string;
    protected readonly PrivacyLevel = PrivacyLevel;
    @Output() readonly edit: EventEmitter<AboutOptionData> = new EventEmitter<AboutOptionData>();
    @Output() readonly delete: EventEmitter<AboutOptionData> = new EventEmitter<AboutOptionData>();
}
