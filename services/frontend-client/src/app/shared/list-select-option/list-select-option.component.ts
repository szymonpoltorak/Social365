import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatRipple } from "@angular/material/core";
import { NgClass } from "@angular/common";
import { RouteOption } from "@interfaces/profile/route-option.interface";
import { RouterLink } from "@angular/router";

@Component({
    selector: 'app-list-select-option',
    standalone: true,
    imports: [
        MatRipple,
        NgClass,
        RouterLink
    ],
    templateUrl: './list-select-option.component.html',
    styleUrl: './list-select-option.component.scss'
})
export class ListSelectOptionComponent {
    @Output() clickEvent: EventEmitter<RouteOption> = new EventEmitter<RouteOption>();
    @Input() isSelected: boolean = false;
    @Input() option!: RouteOption;

    changeSelectionStatus(): void {
        this.clickEvent.emit(this.option);
    }
}
