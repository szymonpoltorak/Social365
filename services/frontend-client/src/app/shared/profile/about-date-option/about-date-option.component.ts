import { Component, Input } from '@angular/core';
import { AboutOption } from "@core/data/profile/about/AboutOption";
import { AboutOptionData } from "@core/data/profile/about/AboutOptionData";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatIcon } from "@angular/material/icon";
import { MatButton } from "@angular/material/button";
import { MatFormField, MatInputModule } from "@angular/material/input";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
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
    date = new FormControl(new Date());

    editData($event: AboutOptionData) {

    }

    deleteAboutDate($event: AboutOptionData) {

    }
}
