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

    constructor(private routingService: RoutingService) {
    }

    ngOnInit(): void {
        this.canEdit = this.routingService.isCurrentUserAbleToEdit();
    }

    editData(): void {
        this.option.isBeingEdited = true;
    }

    deleteAboutDate(): void {

    }
}
