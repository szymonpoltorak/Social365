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
import { DetailsType } from "@enums/profile/details-type.enum";
import { OverviewData } from "@interfaces/profile/about/overview-data.interface";
import { AboutEnumMapperService } from "@api/profile/about/about-enum-mapper.service";

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
    protected readonly AboutOptionType = DetailsType;
    protected options !: AboutOption[];

    constructor(private aboutOverviewService: AboutDataService,
                private aboutEnumMapperService: AboutEnumMapperService,
                private routingService: RoutingService) {
    }

    ngOnInit(): void {
        this.username = this.routingService.getCurrentUsernameForRoute();

        this.aboutOverviewService
            .getOverview(this.username)
            .subscribe((overview: OverviewData) => {
                if (overview.relationshipStatus !== null) {
                    overview.relationshipStatus.label = this.aboutEnumMapperService
                        .mapRelationshipTypeToRelationship(overview.relationshipStatus.label);
                }
                this.options = [
                    {
                        label: "Works at",
                        subLabel: "Current occupation",
                        data: overview.workplace,
                        icon: "work",
                        nullLabel: "Add a workplace",
                        isBeingEdited: false,
                        formControl: new FormControl<string>(this.getValueForForm(overview.workplace)),
                        type: DetailsType.WORKPLACE
                    },
                    {
                        label: "Studied at",
                        subLabel: "Last education institution",
                        data: overview.highSchool,
                        icon: "school",
                        nullLabel: "Add education",
                        isBeingEdited: false,
                        formControl: new FormControl<string>(this.getValueForForm(overview.highSchool)),
                        type: DetailsType.HIGH_SCHOOL
                    },
                    {
                        label: "Lives in",
                        subLabel: "Current city of residence",
                        data: overview.currentCity,
                        icon: "location_city",
                        nullLabel: "Add current city",
                        isBeingEdited: false,
                        formControl: new FormControl<string>(this.getValueForForm(overview.currentCity)),
                        type: DetailsType.CURRENT_CITY
                    },
                    {
                        label: "From",
                        subLabel: "Hometown",
                        data: overview.homeTown,
                        icon: "home",
                        nullLabel: "Add hometown",
                        isBeingEdited: false,
                        formControl: new FormControl<string>(this.getValueForForm(overview.homeTown)),
                        type: DetailsType.HOMETOWN
                    },
                    {
                        label: "",
                        subLabel: "Relationship status",
                        data: overview.relationshipStatus,
                        icon: "favorite",
                        nullLabel: "Add a relationship status",
                        isBeingEdited: false,
                        formControl: new FormControl<string>(this.getValueForForm(overview.relationshipStatus)),
                        type: DetailsType.RELATIONSHIP_STATUS
                    }
                ];
            });
    }

    private getValueForForm(option: Optional<AboutOptionData>): string {
        return option === null ? "" : option.label;
    }

}
