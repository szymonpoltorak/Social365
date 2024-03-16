import { Component, Input } from '@angular/core';
import { AboutOption } from "@core/data/profile/about/AboutOption";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatIcon } from "@angular/material/icon";
import { MatButton } from "@angular/material/button";
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { MatOption, MatSelect } from "@angular/material/select";
import { MatDivider } from "@angular/material/divider";
import { ReactiveFormsModule } from "@angular/forms";
import { AboutOptionData } from "@core/data/profile/about/AboutOptionData";

@Component({
    selector: 'app-about-select-option',
    standalone: true,
    imports: [
        AboutOptionComponent,
        MatIcon,
        MatButton,
        MatFormField,
        MatSelect,
        MatOption,
        MatDivider,
        ReactiveFormsModule,
        MatLabel
    ],
    templateUrl: './about-select-option.component.html',
    styleUrl: './about-select-option.component.scss'
})
export class AboutSelectOptionComponent {
    @Input() selectEnum !: NonNullable<unknown>;
    @Input() option !: AboutOption;
    protected readonly Object = Object;

    editData($event: AboutOptionData) {

    }

    deleteAboutDate($event: AboutOptionData) {

    }
}
