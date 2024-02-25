import { Component, OnInit } from '@angular/core';
import { MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatIcon } from "@angular/material/icon";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { map, Observable, startWith } from "rxjs";
import { AsyncPipe } from "@angular/common";
import { MatMiniFabButton } from "@angular/material/button";
import { RouterLink } from "@angular/router";
import { MatTooltip } from "@angular/material/tooltip";

@Component({
    selector: 'app-feed',
    standalone: true,
    imports: [
        MatInput,
        MatIcon,
        ReactiveFormsModule,
        MatLabel,
        MatAutocompleteModule,
        AsyncPipe,
        MatMiniFabButton,
        RouterLink,
        MatTooltip
    ],
    templateUrl: './feed.component.html',
    styleUrl: './feed.component.scss'
})
export class FeedComponent implements OnInit {
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
