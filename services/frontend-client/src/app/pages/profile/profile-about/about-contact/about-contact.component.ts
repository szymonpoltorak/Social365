import { Component } from '@angular/core';
import { MatCardTitle } from "@angular/material/card";
import { AboutTypicalOptionComponent } from "@shared/profile/about-typical-option/about-typical-option.component";
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatButton } from "@angular/material/button";
import { MatDivider } from "@angular/material/divider";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatOption } from "@angular/material/autocomplete";
import { MatSelect } from "@angular/material/select";
import { Gender } from "@enums/profile/gender.enum";
import { AboutSelectOptionComponent } from "@shared/profile/about-select-option/about-select-option.component";
import { AboutDateOptionComponent } from "@shared/profile/about-date-option/about-date-option.component";
import { DatePipe } from "@angular/common";

@Component({
    selector: 'app-about-contact',
    standalone: true,
    imports: [
        MatCardTitle,
        AboutTypicalOptionComponent,
        AboutOptionComponent,
        MatButton,
        MatDivider,
        MatFormField,
        MatHint,
        MatIcon,
        MatInput,
        MatLabel,
        MatOption,
        MatSelect,
        ReactiveFormsModule,
        AboutSelectOptionComponent,
        AboutDateOptionComponent
    ],
    templateUrl: './about-contact.component.html',
    styleUrl: './about-contact.component.scss'
})
export class AboutContactComponent {
    protected mobileOption: AboutOption = {
        label: "",
        subLabel: "Mobile",
        data: null,
        icon: "mobile",
        nullLabel: "Add a Mobile Number",
        isBeingEdited: false,
        formControl: new FormControl<string>("")
    };
    protected mailOption: AboutOption = {
        label: "",
        subLabel: "Mail",
        data: { label: "shiba@gmail.com", privacyLevel: PrivacyLevel.ONLY_ME },
        icon: "mail",
        nullLabel: "Add your email address",
        isBeingEdited: false,
        formControl: new FormControl<string>("")
    };
    protected genderOption: AboutOption = {
        label: "",
        subLabel: "Gender",
        data: { label: Gender.MALE, privacyLevel: PrivacyLevel.ONLY_ME },
        icon: Gender.MALE.toLowerCase(),
        nullLabel: "Select your gender",
        isBeingEdited: false,
        formControl: new FormControl<string>("")
    };
    protected birthdateOption: AboutOption = {
        label: "",
        subLabel: "Birth date",
        data: { label: new DatePipe("en-Us").transform("2002-11-11")!, privacyLevel: PrivacyLevel.ONLY_ME },
        icon: "cake",
        nullLabel: "Add your birth date",
        isBeingEdited: false,
        formControl: new FormControl<string>(new Date().toISOString())
    };
    protected readonly Gender = Gender;
}
