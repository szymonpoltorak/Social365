import { Component, Input, OnInit } from '@angular/core';
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatIcon } from "@angular/material/icon";
import { MatButton } from "@angular/material/button";
import { MatFormField, MatInputModule } from "@angular/material/input";
import { ReactiveFormsModule } from "@angular/forms";
import { MatDatepicker, MatDatepickerInput, MatDatepickerToggle } from "@angular/material/datepicker";
import { MatDivider } from "@angular/material/divider";
import { RoutingService } from "@services/profile/routing.service";
import { AboutUnfilledOptionComponent } from "@shared/profile/about-unfilled-option/about-unfilled-option.component";
import { AboutDetailsService } from "@api/profile/about/about-details.service";
import { DateOfBirthRequest } from "@interfaces/profile/about/date-of-birth-request.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { Optional } from "@core/types/profile/optional.type";
import { AboutOptionData } from "@interfaces/profile/about/about-option-data.interface";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";
import { DatePipe } from "@angular/common";

@Component({
    selector: 'app-about-date-option',
    standalone: true,
    imports: [
        AboutOptionComponent,
        MatIcon,
        MatButton,
        MatInputModule,
        MatFormField,
        ReactiveFormsModule,
        MatDatepickerInput,
        MatDatepicker,
        MatDatepickerToggle,
        MatDivider,
        AboutUnfilledOptionComponent
    ],
    templateUrl: './about-date-option.component.html',
    styleUrl: './about-date-option.component.scss'
})
export class AboutDateOptionComponent implements OnInit {
    @Input() option!: AboutOption;
    canEdit !: boolean;

    constructor(private routingService: RoutingService,
                private localStorage: LocalStorageService,
                private aboutDetailsService: AboutDetailsService) {
    }

    ngOnInit(): void {
        this.canEdit = this.routingService.isCurrentUserAbleToEdit();
    }

    submitData(): void {
        if (this.option.formControl.value === null) {
            return;
        }
        const request: DateOfBirthRequest = {
            detailsType: this.option.type,
            profileId: this.localStorage.getUserProfileIdFromStorage(),
            dateOfBirth: this.option.formControl.value as Date,
            privacyLevel: this.getPrivacyLevel(this.option.data)
        };

        this.aboutDetailsService
            .updateProfileDateOfBirth(request)
            .subscribe(() => {
                this.option.data = {
                    label: new DatePipe("en-US").transform(this.option.data!.label)!,
                    privacyLevel: request.privacyLevel
                };
            });
    }

    private getPrivacyLevel(data: Optional<AboutOptionData>): PrivacyLevel {
        return data === null ? PrivacyLevel.ONLY_ME : data.privacyLevel;
    }

}
