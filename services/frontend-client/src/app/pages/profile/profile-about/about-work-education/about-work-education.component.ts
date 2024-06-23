import { Component, OnInit } from '@angular/core';
import { MatCardTitle } from "@angular/material/card";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatButton } from "@angular/material/button";
import { MatDivider } from "@angular/material/divider";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatOption } from "@angular/material/autocomplete";
import { MatSelect } from "@angular/material/select";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { AboutTypicalOptionComponent } from "@shared/profile/about-typical-option/about-typical-option.component";
import { DetailsType } from "@enums/profile/details-type.enum";
import { AboutDataService } from "@api/profile/about/about-data.service";
import { RoutingService } from "@services/profile/routing.service";
import { WorkEducation } from "@interfaces/profile/about/work-education.interface";
import { Optional } from "@core/types/profile/optional.type";
import { AboutOptionData } from "@interfaces/profile/about/about-option-data.interface";

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
export class AboutWorkEducationComponent implements OnInit {
    workOption !: AboutOption;
    collegeOption !: AboutOption
    highSchoolOption !: AboutOption;

    constructor(private aboutDataService: AboutDataService,
                private routingService: RoutingService) {
    }

    ngOnInit(): void {
        const username: string = this.routingService.getCurrentUsernameForRoute();

        this.aboutDataService
            .getWorkEducation(username)
            .subscribe((data: WorkEducation) => {
                this.workOption = {
                    label: "Works at",
                    subLabel: "Current occupation",
                    data: data.workplace,
                    icon: "work",
                    nullLabel: "Add a workplace",
                    isBeingEdited: false,
                    formControl: new FormControl<string>(this.getValueForForm(data.workplace)),
                    type: DetailsType.WORKPLACE
                };
                this.collegeOption = {
                    label: "Studied at",
                    subLabel: "The college I attended",
                    data: data.college,
                    icon: "school",
                    nullLabel: "Add college",
                    isBeingEdited: false,
                    formControl: new FormControl<string>(this.getValueForForm(data.college)),
                    type: DetailsType.COLLEGE
                };
                this.highSchoolOption = {
                    label: "Went to",
                    subLabel: "Went to high school",
                    data: data.highSchool,
                    icon: "history_edu",
                    nullLabel: "Add high school",
                    isBeingEdited: false,
                    formControl: new FormControl<string>(this.getValueForForm(data.highSchool)),
                    type: DetailsType.HIGH_SCHOOL
                };
            });
    }

    private getValueForForm(option: Optional<AboutOptionData>): string {
        return option === null ? "" : option.label;
    }

}
