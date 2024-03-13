import { Component } from '@angular/core';
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatCardTitle } from "@angular/material/card";
import { MatIcon } from "@angular/material/icon";
import { OverviewData } from "@core/data/profile/OverviewData";
import { Relationship } from "@enums/profile/Relationship";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { AboutOptionComponent } from "@pages/profile/profile-about/about-overview/about-option/about-option.component";
import { PrivacyLevel } from "@enums/profile/PrivacyLevel";
import { AboutOption } from "@core/data/profile/AboutOption";

@Component({
    selector: 'app-about-overview',
    standalone: true,
    imports: [
        MatButton,
        MatCardTitle,
        MatIcon,
        MatIconButton,
        MatMenu,
        MatMenuItem,
        MatMenuTrigger,
        AboutOptionComponent
    ],
    templateUrl: './about-overview.component.html',
    styleUrl: './about-overview.component.scss'
})
export class AboutOverviewComponent {
    protected overview: OverviewData = {
        workplace: { label: "Works at Google", privacyLevel: PrivacyLevel.PUBLIC },
        school: { label: "University of California, Los Angeles", privacyLevel: PrivacyLevel.FRIENDS },
        currentCity: null,
        hometown: { label: "Los Angeles, California", privacyLevel: PrivacyLevel.ONLY_ME },
        relationship: { label: Relationship.IN_A_RELATIONSHIP, privacyLevel: PrivacyLevel.PUBLIC }
    };
    protected options: AboutOption[] = [
        {
            label: "Works at",
            subLabel: "Current occupation",
            data: this.overview.workplace,
            icon: "work",
            nullLabel: "Add a workplace"
        },
        {
            label: "Studied at",
            subLabel: "Last education institution",
            data: this.overview.school,
            icon: "school",
            nullLabel: "Add education"
        },
        {
            label: "Lives in",
            subLabel: "Current city of residence",
            data: this.overview.currentCity,
            icon: "location_city",
            nullLabel: "Add current city"
        },
        { label: "From", subLabel: "Hometown", data: this.overview.hometown, icon: "home", nullLabel: "Add hometown" },
        {
            label: "",
            subLabel: "Relationship status",
            data: this.overview.relationship,
            icon: "favorite",
            nullLabel: "Add a relationship status"
        }
    ];
}
