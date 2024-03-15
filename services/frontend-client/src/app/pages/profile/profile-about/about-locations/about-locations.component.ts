import { Component } from '@angular/core';
import { MatCardTitle } from "@angular/material/card";
import {
    AboutTypicalOptionComponent
} from "@pages/profile/profile-about/about-work-education/about-typical-option/about-typical-option.component";
import { AboutOption } from "@core/data/profile/about/AboutOption";
import { FormControl } from "@angular/forms";
import { PrivacyLevel } from "@enums/profile/PrivacyLevel";

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
