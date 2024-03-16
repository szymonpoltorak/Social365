import { Component, Input } from '@angular/core';
import { AboutOption } from "@core/data/profile/about/AboutOption";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatIcon } from "@angular/material/icon";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { ReactiveFormsModule } from "@angular/forms";
import { MatDivider } from "@angular/material/divider";
import { MatButton } from "@angular/material/button";
import { AboutOptionData } from "@core/data/profile/about/AboutOptionData";

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

    editData(event: AboutOptionData): void {

    }

    deleteAboutDate(event: AboutOptionData): void {

    }
}
