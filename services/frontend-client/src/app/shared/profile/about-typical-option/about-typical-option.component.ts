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
    @Input() option !: AboutOption;
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
