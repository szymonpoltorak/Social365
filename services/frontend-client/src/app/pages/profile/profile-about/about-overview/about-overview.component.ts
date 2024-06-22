import { Component, OnInit } from '@angular/core';
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatCardTitle } from "@angular/material/card";
import { MatIcon } from "@angular/material/icon";
import { Relationship } from "@enums/profile/relationship.enum";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatDivider } from "@angular/material/divider";
import { AboutOptionData } from "@interfaces/profile/about/about-option-data.interface";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { Optional } from "@core/types/profile/optional.type";
import { MatOption, MatSelect } from "@angular/material/select";
import { AboutSelectOptionComponent } from "@shared/profile/about-select-option/about-select-option.component";
import { AboutTypicalOptionComponent } from "@shared/profile/about-typical-option/about-typical-option.component";
import { AboutDataService } from "@api/profile/about/about-data.service";
import { RoutingService } from '@core/services/profile/routing.service';
import { AboutWorkplaceComponent } from "@shared/profile/about-workplace/about-workplace.component";

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
        MatOption,
        AboutSelectOptionComponent,
        AboutTypicalOptionComponent,
        AboutWorkplaceComponent
    ],
    templateUrl: './about-overview.component.html',
    styleUrl: './about-overview.component.scss'
})
export class AboutOverviewComponent implements OnInit {

    protected username !: string;
    protected readonly Relationship = Relationship;
    protected options !: AboutOption[];

    constructor(private aboutOverviewService: AboutDataService,
                private routingService: RoutingService) {
    }

    ngOnInit(): void {
        this.username = this.routingService.getCurrentUsernameForRoute();

        this.aboutOverviewService
            .getOverview(this.username)
            .subscribe(overview => {
                this.options = [
                    {
                        label: "Works at",
                        subLabel: "Current occupation",
                        data: overview.workplace,
                        icon: "work",
                        nullLabel: "Add a workplace",
                        isBeingEdited: false,
                        formControl: new FormControl<string>(this.getValueForForm(overview.workplace))
                    },
                    {
                        label: "Studied at",
                        subLabel: "Last education institution",
                        data: overview.highSchool,
                        icon: "school",
                        nullLabel: "Add education",
                        isBeingEdited: false,
                        formControl: new FormControl<string>(this.getValueForForm(overview.highSchool))
                    },
                    {
                        label: "Lives in",
                        subLabel: "Current city of residence",
                        data: overview.currentCity,
                        icon: "location_city",
                        nullLabel: "Add current city",
                        isBeingEdited: false,
                        formControl: new FormControl<string>(this.getValueForForm(overview.currentCity))
                    },
                    {
                        label: "From",
                        subLabel: "Hometown",
                        data: overview.homeTown,
                        icon: "home",
                        nullLabel: "Add hometown",
                        isBeingEdited: false,
                        formControl: new FormControl<string>(this.getValueForForm(overview.homeTown))
                    },
                    {
                        label: "",
                        subLabel: "Relationship status",
                        data: overview.relationshipStatus,
                        icon: "favorite",
                        nullLabel: "Add a relationship status",
                        isBeingEdited: false,
                        formControl: new FormControl<string>(this.getValueForForm(overview.relationshipStatus))
                    }
                ];
            });
    }

    deleteAboutDate(event: AboutOptionData): void {
        const optionIndex: number = this.options.map(a => a.data).indexOf(event);

        if (optionIndex === -1) {
            return;
        }
        this.options[optionIndex].data = null;
    }

    editData(option: AboutOptionData): void {
        const optionIndex: number = this.options.map(a => a.data).indexOf(option);

        if (optionIndex === -1) {
            return;
        }
        this.options[optionIndex].isBeingEdited = true;
    }

    private getValueForForm(option: Optional<AboutOptionData>): string {
        return option === null ? "" : option.label;
    }
}
