import { Component } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { MatChipListbox, MatChipOption } from "@angular/material/chips";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { AboutOverviewComponent } from "@pages/profile/profile-about/about-overview/about-overview.component";
import {
    AboutWorkEducationComponent
} from "@pages/profile/profile-about/about-work-education/about-work-education.component";
import { AboutLocationsComponent } from "@pages/profile/profile-about/about-locations/about-locations.component";
import { AboutContactComponent } from "@pages/profile/profile-about/about-contact/about-contact.component";
import {
    AboutRelationshipComponent
} from "@pages/profile/profile-about/about-relationship/about-relationship.component";
import { RouterLink, RouterOutlet } from "@angular/router";
import { RouteOption } from "@core/data/profile/RouteOption";
import { RouterPaths } from "@enums/RouterPaths";

@Component({
    selector: 'app-profile-about',
    standalone: true,
    imports: [
        MatCard,
        MatCardHeader,
        MatCardTitle,
        MatCardContent,
        MatChipListbox,
        MatChipOption,
        MatButton,
        MatIcon,
        AboutOverviewComponent,
        AboutWorkEducationComponent,
        AboutLocationsComponent,
        AboutContactComponent,
        AboutRelationshipComponent,
        RouterOutlet,
        RouterLink
    ],
    templateUrl: './profile-about.component.html',
    styleUrl: './profile-about.component.scss'
})
export class ProfileAboutComponent {
    protected readonly options: RouteOption[] = [
        { label: "Overview", route: RouterPaths.PROFILE_ABOUT_OVERVIEW },
        { label: "Work and Education", route: RouterPaths.PROFILE_ABOUT_WORK_EDUCATION },
        { label: "Places You've Lived", route: RouterPaths.PROFILE_ABOUT_LOCATIONS },
        { label: "Contact and Basic Info", route: RouterPaths.PROFILE_ABOUT_CONTACT },
        { label: "Family and Relationships", route: RouterPaths.PROFILE_ABOUT_RELATIONSHIP }
    ];
    protected selectedOption: RouteOption = this.options[0];
}
