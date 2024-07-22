import { Component, Input, OnInit } from '@angular/core';
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { MatIcon } from "@angular/material/icon";
import { MatButton } from "@angular/material/button";
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { MatOption, MatSelect } from "@angular/material/select";
import { MatDivider } from "@angular/material/divider";
import { ReactiveFormsModule } from "@angular/forms";
import { RoutingService } from "@services/profile/routing.service";
import { AboutUnfilledOptionComponent } from "@shared/profile/about-unfilled-option/about-unfilled-option.component";
import { AboutApiHelperService } from "@api/profile/about/about-api-helper.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";

@Component({
    selector: 'app-about-select-option',
    standalone: true,
    imports: [
        AboutOptionComponent,
        MatIcon,
        MatButton,
        MatFormField,
        MatSelect,
        MatOption,
        MatDivider,
        ReactiveFormsModule,
        MatLabel,
        AboutUnfilledOptionComponent
    ],
    templateUrl: './about-select-option.component.html',
    styleUrl: './about-select-option.component.scss'
})
export class AboutSelectOptionComponent implements OnInit {
    @Input() selectEnum !: NonNullable<unknown>;
    @Input() option !: AboutOption;
    canEdit !: boolean;
    protected readonly Object = Object;

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

    submitData(): void {
        if (this.option.formControl.value === null) {
            this.snackBar.open("Please select a value", "Close");

            return;
        }
        const request: any = this.aboutApiHelperService
            .mapAboutOptionToAboutOptionRequest(this.option.formControl.value, this.option);

        this.aboutApiHelperService
            .updateAboutOption(request)
            .subscribe(() => {
                this.option.data = {
                    label: this.option.formControl.value as string,
                    privacyLevel: request.privacyLevel
                }
                this.option.isBeingEdited = false;

                this.snackBar.open("Successfully updated", "Close");
            });
    }
}
