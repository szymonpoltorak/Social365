import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatRipple } from "@angular/material/core";
import { NgClass } from "@angular/common";

@Component({
    selector: 'app-list-select-option',
    standalone: true,
    imports: [
        MatRipple,
        NgClass
    ],
    templateUrl: './list-select-option.component.html',
    styleUrl: './list-select-option.component.scss'
})
export class ListSelectOptionComponent {
    @Input() title !: string;
    @Output() clickEvent: EventEmitter<string> = new EventEmitter<string>();
    @Input() isSelected: boolean = false;

    changeSelectionStatus(): void {
        this.clickEvent.emit(this.title);
    }
}
