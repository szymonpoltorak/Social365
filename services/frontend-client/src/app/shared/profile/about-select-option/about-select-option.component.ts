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
    protected readonly Object = Object;
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
