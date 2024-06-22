import { Component, Input, OnInit } from '@angular/core';
import { AboutOption } from "@interfaces/profile/about/about-option.interface";
import { AboutOptionComponent } from "@shared/profile/about-option/about-option.component";
import { AboutUnfilledOptionComponent } from "@shared/profile/about-unfilled-option/about-unfilled-option.component";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButton } from "@angular/material/button";
import { MatDivider } from "@angular/material/divider";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { RoutingService } from "@services/profile/routing.service";
import { WorkPlaceRequest } from "@interfaces/profile/about/workplace-request.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { PrivacyLevel } from "@enums/profile/privacy-level.enum";
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
    selector: 'app-about-workplace',
    standalone: true,
    imports: [
        AboutOptionComponent,
        AboutUnfilledOptionComponent,
        FormsModule,
        MatButton,
        MatDivider,
        MatFormField,
        MatHint,
        MatIcon,
        MatInput,
        MatLabel,
        ReactiveFormsModule
    ],
    templateUrl: './about-workplace.component.html',
    styleUrl: './about-workplace.component.scss'
})
export class AboutWorkplaceComponent implements OnInit {

    @Input() option !: AboutOption;
    canEdit !: boolean;
    positionFormControl !: FormControl<string | null>;
    workplaceFormControl !: FormControl<string | null>;

    constructor(private routingService: RoutingService,
                private matSnackBar: MatSnackBar,
                private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        this.canEdit = this.routingService.isCurrentUserAbleToEdit();

        if (this.option.data === null) {
            this.positionFormControl = new FormControl<string>("");

            this.workplaceFormControl = new FormControl<string>("");

            return;
        }
        const data: string[] = this.option.data.label.split(" at ");

        this.positionFormControl = new FormControl<string>(data[0]);

        this.workplaceFormControl = new FormControl<string>(data[1]);
    }

    editData(): void {
        this.option.isBeingEdited = true;
    }

    deleteAboutDate(): void {

    }

    updateWorkplace(): void {
        const position: string | null = this.positionFormControl.value;
        const workplace: string | null = this.workplaceFormControl.value;

        if (position === null || workplace === null) {
            this.matSnackBar.open("Please fill in all fields", "Close");

            return;
        }
        const workplaceRequest: WorkPlaceRequest = {
            position: position,
            workplace: workplace,
            profileId: this.localStorage.getUserProfileIdFromStorage(),
            privacyLevel: PrivacyLevel.ONLY_ME
        };
        console.log(workplaceRequest);
        this.option.isBeingEdited = false;
    }
}
