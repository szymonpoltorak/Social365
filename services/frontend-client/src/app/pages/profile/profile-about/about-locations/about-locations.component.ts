import { Component } from '@angular/core';
import { MatCardTitle } from "@angular/material/card";
import {
    AboutTypicalOptionComponent
} from "@shared/profile/about-typical-option/about-typical-option.component";
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { FormControl } from "@angular/forms";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

@Component({
    selector: 'app-about-locations',
    standalone: true,
    imports: [
        MatCardTitle,
        AboutTypicalOptionComponent
    ],
    templateUrl: './about-locations.component.html',
    styleUrl: './about-locations.component.scss'
})
export class AboutLocationsComponent {
    currentCityOption: AboutOption = {
        label: "Lives in",
        subLabel: "Current city",
        data: null,
        icon: "location_city",
        nullLabel: "Add a workplace",
        isBeingEdited: false,
        formControl: new FormControl<string>("")
    };
    homeTownOption: AboutOption = {
        label: "Born in",
        subLabel: "I am from",
        data: { label: "Bornholm", privacyLevel: PrivacyLevel.ONLY_ME },
        icon: "home",
        nullLabel: "Add hometown",
        isBeingEdited: false,
        formControl: new FormControl<string>("")
    };
}
