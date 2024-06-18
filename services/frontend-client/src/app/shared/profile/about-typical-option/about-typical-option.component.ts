import { Component, Input } from '@angular/core';
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatIcon } from "@angular/material/icon";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { ReactiveFormsModule } from "@angular/forms";
import { MatDivider } from "@angular/material/divider";
import { MatButton } from "@angular/material/button";

@Component({
    selector: 'app-about-typical-option',
    standalone: true,
    imports: [
        AboutOptionComponent,
        MatIcon,
        MatFormField,
        MatInput,
        ReactiveFormsModule,
        MatDivider,
        MatButton,
        MatLabel,
        MatHint
    ],
    templateUrl: './about-typical-option.component.html',
    styleUrl: './about-typical-option.component.scss'
})
export class AboutTypicalOptionComponent {
    @Input() option !: AboutOption;
    @Input() canEdit !: boolean;

    editData(): void {
        this.option.isBeingEdited = true;
    }

    deleteAboutDate(): void {

    }
}
