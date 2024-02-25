import { Component, OnInit } from '@angular/core';
import { AsyncPipe } from "@angular/common";
import { MatAutocomplete, MatAutocompleteTrigger, MatOption } from "@angular/material/autocomplete";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { MatMiniFabButton } from "@angular/material/button";
import { MatTooltip } from "@angular/material/tooltip";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { RouterLink } from "@angular/router";
import { map, Observable, startWith } from "rxjs";

@Component({
    selector: 'app-toolbar',
    standalone: true,
    imports: [
        AsyncPipe,
        MatAutocomplete,
        MatAutocompleteTrigger,
        MatIcon,
        MatInput,
        MatMenu,
        MatMenuItem,
        MatMiniFabButton,
        MatOption,
        MatTooltip,
        ReactiveFormsModule,
        RouterLink,
        MatMenuTrigger
    ],
    templateUrl: './toolbar.component.html',
    styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent implements OnInit {
    protected readonly searchSocialControl: FormControl<string> = new FormControl();
    options: string[] = ['Szymon Półtorak', 'Jacek Kowalski', 'John Smith', "Stefan Nowak"];
    filteredOptions !: Observable<string[]>;

    ngOnInit(): void {
        this.filteredOptions = this.searchSocialControl.valueChanges.pipe(
            startWith(''),
            map(value => this._filter(value || '')),
        );
    }

    private _filter(value: string): string[] {
        const filterValue = value.toLowerCase();

        return this.options.filter(option => option.toLowerCase().includes(filterValue));
    }
}
