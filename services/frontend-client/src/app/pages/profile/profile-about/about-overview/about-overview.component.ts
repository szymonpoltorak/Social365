import { Component } from '@angular/core';
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatCardTitle } from "@angular/material/card";
import { MatIcon } from "@angular/material/icon";
import { OverviewData } from "@core/data/profile/about/OverviewData";
import { Relationship } from "@enums/profile/Relationship";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { AboutOptionComponent } from "@pages/profile/profile-about/about-overview/about-option/about-option.component";
import { PrivacyLevel } from "@enums/profile/PrivacyLevel";
import { AboutOption } from "@core/data/profile/about/AboutOption";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatDivider } from "@angular/material/divider";
import { AboutOptionData } from "@core/data/profile/about/AboutOptionData";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { Optional } from "@core/types/profile/Optional";
import { MatOption, MatSelect } from "@angular/material/select";

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
        AboutOptionComponent,
        MatFormField,
        MatInput,
        MatLabel,
        MatDivider,
        MatHint,
        ReactiveFormsModule,
        MatSelect,
        MatOption
    ],
    templateUrl: './about-overview.component.html',
    styleUrl: './about-overview.component.scss'
})
export class AboutOverviewComponent {
    protected readonly Relationship = Relationship;
    protected readonly Object = Object;
    protected overview: OverviewData = {
        workplace: { label: "Google", privacyLevel: PrivacyLevel.PUBLIC },
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
            nullLabel: "Add a workplace",
            isBeingEdited: false,
            formControl: new FormControl<string>(this.getValueForForm(this.overview.workplace))
        },
        {
            label: "Studied at",
            subLabel: "Last education institution",
            data: this.overview.school,
            icon: "school",
            nullLabel: "Add education",
            isBeingEdited: false,
            formControl: new FormControl<string>(this.getValueForForm(this.overview.school))
        },
        {
            label: "Lives in",
            subLabel: "Current city of residence",
            data: this.overview.currentCity,
            icon: "location_city",
            nullLabel: "Add current city",
            isBeingEdited: false,
            formControl: new FormControl<string>(this.getValueForForm(this.overview.currentCity))
        },
        {
            label: "From",
            subLabel: "Hometown",
            data: this.overview.hometown,
            icon: "home",
            nullLabel: "Add hometown",
            isBeingEdited: false,
            formControl: new FormControl<string>(this.getValueForForm(this.overview.hometown))
        },
        {
            label: "",
            subLabel: "Relationship status",
            data: this.overview.relationship,
            icon: "favorite",
            nullLabel: "Add a relationship status",
            isBeingEdited: false,
            formControl: new FormControl<string>(this.getValueForForm(this.overview.relationship))
        }
    ];

    deleteAboutDate(event: AboutOptionData): void {
        const optionIndex: number = this.options.map(a => a.data).indexOf(event);

        if (optionIndex !== -1) {
            this.options[optionIndex].data = null;
        }
    }

    editData(option: AboutOptionData): void {
        const optionIndex: number = this.options.map(a => a.data).indexOf(option);

        this.options[optionIndex].isBeingEdited = true;
    }

    private getValueForForm(option: Optional<AboutOptionData>): string {
        return option === null ? "" : option.label;
    }
}
