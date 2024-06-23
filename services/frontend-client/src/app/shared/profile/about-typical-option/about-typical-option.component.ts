import { Component, Input, OnInit } from '@angular/core';
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatIcon } from "@angular/material/icon";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { ReactiveFormsModule } from "@angular/forms";
import { MatDivider } from "@angular/material/divider";
import { MatButton } from "@angular/material/button";
import { AboutUnfilledOptionComponent } from "@shared/profile/about-unfilled-option/about-unfilled-option.component";
import { RoutingService } from "@services/profile/routing.service";
import { AboutApiHelperService } from "@api/profile/about/about-api-helper.service";
import { Either } from "@core/types/feed/either.type";
import { MatSnackBar } from "@angular/material/snack-bar";
import { DetailsType } from "@enums/profile/details-type.enum";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

@Component({
    selector: 'app-about-typical-option',
    standalone: true,
    imports: [
        AboutOptionComponent,
        MatIcon,
        MatFormField,
        MatInput,
        ReactiveFormsModule,
        MatDivider,
        MatButton,
        MatLabel,
        MatHint,
        AboutUnfilledOptionComponent
    ],
    templateUrl: './about-typical-option.component.html',
    styleUrl: './about-typical-option.component.scss'
})
export class AboutTypicalOptionComponent implements OnInit {

    protected readonly DetailsType = DetailsType;
    @Input() option !: AboutOption;
    canEdit: boolean = false;

    constructor(private routingService: RoutingService,
                private snackBar: MatSnackBar,
                private aboutApiHelperService: AboutApiHelperService) {
    }

    ngOnInit(): void {
        this.canEdit = this.routingService.isCurrentUserAbleToEdit();
    }

    deleteAboutDate(): void {
        if (this.option.formControl.value === null) {
            return;
        }
        this.aboutApiHelperService
            .deleteAboutOption(this.option.type)
            .subscribe(() => {
                this.option.data = null;
                this.option.isBeingEdited = false;

                this.snackBar.open("Successfully deleted", "Close");
            });
    }

    submitData(): void {
        if (this.option.formControl.value === null) {
            this.snackBar.open("Please enter a valid value", "Close");

            return;
        }
        const detailsValue: Either<string, Date> = this.option.formControl.value;
        const request: any = this.aboutApiHelperService
            .mapAboutOptionToAboutOptionRequest(detailsValue, this.option);

        this.aboutApiHelperService
            .updateAboutOption(request)
            .subscribe(() => {
                this.option.data = {
                    label: detailsValue as string,
                    privacyLevel: request.privacyLevel
                }
                this.option.isBeingEdited = false;

                this.snackBar.open("Successfully updated", "Close");
            });
    }

    updatePrivacyLevel(event: PrivacyLevel): void {
        if (this.option.data === null) {
            return;
        }
        const request: any = this.aboutApiHelperService
            .mapAboutOptionToAboutOptionRequest(this.option.data.label, this.option);

        request.privacyLevel = event;

        this.aboutApiHelperService
            .updateAboutOption(request)
            .subscribe(() => {
                this.option.data!.privacyLevel = request.privacyLevel;
                this.option.isBeingEdited = false;

                this.snackBar.open("Successfully updated", "Close");
            });
    }
}
