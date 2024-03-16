import { Component, Input } from '@angular/core';
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatIcon } from "@angular/material/icon";
import { MatButton } from "@angular/material/button";
import { MatFormField, MatInputModule } from "@angular/material/input";
import { ReactiveFormsModule } from "@angular/forms";
import { MatDatepicker, MatDatepickerInput, MatDatepickerToggle } from "@angular/material/datepicker";
import { MatDivider } from "@angular/material/divider";

@Component({
    selector: 'app-about-date-option',
    standalone: true,
    imports: [
        AboutOptionComponent,
        MatIcon,
        MatButton,
        MatInputModule,
        MatFormField,
        ReactiveFormsModule,
        MatDatepickerInput,
        MatDatepicker,
        MatDatepickerToggle,
        MatDivider
    ],
    templateUrl: './about-date-option.component.html',
    styleUrl: './about-date-option.component.scss'
})
export class AboutDateOptionComponent {
    @Input() option!: AboutOption;

    editData(): void {
        this.option.isBeingEdited = true;
    }

    deleteAboutDate(): void {

    }
}
