import { Component } from '@angular/core';
import { MatCardTitle } from "@angular/material/card";
import { AboutOptionComponent } from "@pages/profile/profile-about/about-overview/about-option/about-option.component";
import { MatButton } from "@angular/material/button";
import { MatDivider } from "@angular/material/divider";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatOption } from "@angular/material/autocomplete";
import { MatSelect } from "@angular/material/select";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { AboutOptionData } from "@core/data/profile/about/AboutOptionData";
import { AboutOption } from "@core/data/profile/about/AboutOption";
import {
    AboutTypicalOptionComponent
} from "@pages/profile/profile-about/about-work-education/about-typical-option/about-typical-option.component";
import { PrivacyLevel } from "@enums/profile/PrivacyLevel";

@Component({
    selector: 'app-about-work-education',
    standalone: true,
    imports: [
        MatCardTitle,
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
        AboutTypicalOptionComponent
    ],
    templateUrl: './about-work-education.component.html',
    styleUrl: './about-work-education.component.scss'
})
export class AboutWorkEducationComponent {
    workOption: AboutOption = {
        label: "Works at",
        subLabel: "Current occupation",
        data: { label: "Google", privacyLevel: PrivacyLevel.ONLY_ME },
        icon: "work",
        nullLabel: "Add a workplace",
        isBeingEdited: false,
        formControl: new FormControl<string>("")
    };
    collegeOption: AboutOption = {
        label: "Studied at",
        subLabel: "The college I attended",
        data: { label: "Harvard", privacyLevel: PrivacyLevel.ONLY_ME },
        icon: "school",
        nullLabel: "Add college",
        isBeingEdited: false,
        formControl: new FormControl<string>("")
    };
    highSchoolOption: AboutOption = {
        label: "Went to",
        subLabel: "Went to high school",
        data: null,
        icon: "history_edu",
        nullLabel: "Add high school",
        isBeingEdited: false,
        formControl: new FormControl<string>("")
    };
}
