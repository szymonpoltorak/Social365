import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatIcon } from "@angular/material/icon";
import { MatIconButton } from "@angular/material/button";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { AboutOptionData } from "@interfaces/profile/about/about-option-data.interface";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

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
    @Input() canDelete: boolean = true;
    @Output() readonly edit: EventEmitter<void> = new EventEmitter<void>();
    @Output() readonly delete: EventEmitter<void> = new EventEmitter<void>();
    @Output() readonly changePrivacyLevel: EventEmitter<PrivacyLevel> = new EventEmitter<PrivacyLevel>();
    protected readonly PrivacyLevel = PrivacyLevel;

    changePrivacyLevelEmitter(privacyLevel: PrivacyLevel): void {
        if (privacyLevel === this.option.privacyLevel) {
            return;
        }
        this.changePrivacyLevel.emit(privacyLevel);
    }
}
