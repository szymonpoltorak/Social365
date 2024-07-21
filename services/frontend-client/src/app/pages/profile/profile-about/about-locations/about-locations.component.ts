import { Component, OnInit } from '@angular/core';
import { MatCardTitle } from "@angular/material/card";
import {
    AboutTypicalOptionComponent
} from "@shared/profile/about-typical-option/about-typical-option.component";
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { FormControl } from "@angular/forms";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";
import { DetailsType } from "@enums/profile/details-type.enum";
import { AboutDataService } from "@api/profile/about/about-data.service";
import { RoutingService } from "@services/profile/routing.service";
import { Locations } from "@interfaces/profile/about/locations.interface";
import { Optional } from "@core/types/profile/optional.type";
import { AboutOptionData } from "@interfaces/profile/about/about-option-data.interface";
import { MatProgressSpinner } from "@angular/material/progress-spinner";

@Component({
    selector: 'app-about-locations',
    standalone: true,
    imports: [
        MatCardTitle,
        AboutTypicalOptionComponent,
        MatProgressSpinner
    ],
    templateUrl: './about-locations.component.html',
    styleUrl: './about-locations.component.scss'
})
export class AboutLocationsComponent implements OnInit {

    currentCityOption: AboutOption = {
        label: "Lives in",
        subLabel: "Current city",
        data: null,
        icon: "location_city",
        nullLabel: "Add a current city",
        isBeingEdited: false,
        formControl: new FormControl<string>(""),
        type: DetailsType.CURRENT_CITY
    };
    homeTownOption: AboutOption = {
        label: "Born in",
        subLabel: "I am from",
        data: { label: "Bornholm", privacyLevel: PrivacyLevel.ONLY_ME },
        icon: "home",
        nullLabel: "Add hometown",
        isBeingEdited: false,
        formControl: new FormControl<string>(""),
        type: DetailsType.HOMETOWN
    };

    constructor(private aboutDataService: AboutDataService,
                private routingService: RoutingService) {
    }

    ngOnInit(): void {
        const username: string = this.routingService.getCurrentUsernameForRoute();

        this.aboutDataService
            .getLocations(username)
            .subscribe((data: Locations) => {
                this.currentCityOption = {
                    label: "Lives in",
                    subLabel: "Current city",
                    data: data.currentCity,
                    icon: "location_city",
                    nullLabel: "Add a current city",
                    isBeingEdited: false,
                    formControl: new FormControl<string>(this.getValueForForm(data.currentCity)),
                    type: DetailsType.CURRENT_CITY
                };
                this.homeTownOption = {
                    label: "Born in",
                    subLabel: "I am from",
                    data: data.homeTown,
                    icon: "home",
                    nullLabel: "Add hometown",
                    isBeingEdited: false,
                    formControl: new FormControl<string>(this.getValueForForm(data.homeTown)),
                    type: DetailsType.HOMETOWN
                };
            })
    }

    private getValueForForm(option: Optional<AboutOptionData>): string {
        return option === null ? "" : option.label;
    }

}
