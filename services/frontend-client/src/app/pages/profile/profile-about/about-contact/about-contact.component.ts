import { Component, OnInit } from '@angular/core';
import { MatCardTitle } from "@angular/material/card";
import { AboutTypicalOptionComponent } from "@shared/profile/about-typical-option/about-typical-option.component";
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatButton } from "@angular/material/button";
import { MatDivider } from "@angular/material/divider";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatOption } from "@angular/material/autocomplete";
import { MatSelect } from "@angular/material/select";
import { Gender } from "@enums/profile/gender.enum";
import { AboutSelectOptionComponent } from "@shared/profile/about-select-option/about-select-option.component";
import { AboutDateOptionComponent } from "@shared/profile/about-date-option/about-date-option.component";
import { DatePipe } from "@angular/common";
import { DetailsType } from "@enums/profile/details-type.enum";
import { RoutingService } from "@services/profile/routing.service";
import { AboutDataService } from "@api/profile/about/about-data.service";
import { Optional } from "@core/types/profile/optional.type";
import { AboutOptionData } from "@interfaces/profile/about/about-option-data.interface";
import { ContactInfo } from "@interfaces/profile/about/contact-info.interface";
import { AboutEnumMapperService } from "@api/profile/about/about-enum-mapper.service";

@Component({
    selector: 'app-about-contact',
    standalone: true,
    imports: [
        MatCardTitle,
        AboutTypicalOptionComponent,
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
        AboutSelectOptionComponent,
        AboutDateOptionComponent
    ],
    templateUrl: './about-contact.component.html',
    styleUrl: './about-contact.component.scss'
})
export class AboutContactComponent implements OnInit {

    protected mobileOption !: AboutOption;
    protected mailOption !: AboutOption;
    protected genderOption !: AboutOption;
    protected birthdateOption !: AboutOption;
    protected readonly Gender = Gender;

    constructor(private routingService: RoutingService,
                private aboutEnumMapperService: AboutEnumMapperService,
                private aboutDataService: AboutDataService) {
    }

    ngOnInit(): void {
        const username: string = this.routingService.getCurrentUsernameForRoute();

        this.aboutDataService
            .getContactInfo(username)
            .subscribe((data: ContactInfo) => {
                if (data.gender !== null) {
                    data.gender.label = this.aboutEnumMapperService
                        .mapRelationshipTypeToRelationship(data.gender.label);
                }
                this.mobileOption = {
                    label: "",
                    subLabel: "Mobile",
                    data: data.mobile,
                    icon: "phone",
                    nullLabel: "Add a Mobile Number",
                    isBeingEdited: false,
                    formControl: new FormControl<string>(this.getValueForForm(data.mobile)),
                    type: DetailsType.PHONE
                };
                this.mailOption = {
                    label: "",
                    subLabel: "Mail",
                    data: data.email,
                    icon: "mail",
                    nullLabel: "Add your email address",
                    isBeingEdited: false,
                    formControl: new FormControl<string>(this.getValueForForm(data.email)),
                    type: DetailsType.MAIL
                };
                this.genderOption = {
                    label: "",
                    subLabel: "Gender",
                    data: data.gender,
                    icon: Gender.MALE.toLowerCase(),
                    nullLabel: "Select your gender",
                    isBeingEdited: false,
                    formControl: new FormControl<string>(this.getValueForForm(data.gender)),
                    type: DetailsType.GENDER
                };
                this.birthdateOption = this.buildBirthdateOption(data.birthDate);
            });
    }

    private buildBirthdateOption(data: AboutOptionData): AboutOption {
        if (data.label !== null) {
            data.label = new DatePipe("en-US").transform(data.label)!;
        } else {
            data.label = "";
        }
        return {
            label: "",
            subLabel: "Birth date",
            data: data,
            icon: "cake",
            nullLabel: "Add your birth date",
            isBeingEdited: false,
            formControl: new FormControl<string>(this.getValueForForm(data)),
            type: DetailsType.BIRTHDAY
        };
    }

    private getValueForForm(option: Optional<AboutOptionData>): string {
        return option === null ? "" : option.label;
    }

}
