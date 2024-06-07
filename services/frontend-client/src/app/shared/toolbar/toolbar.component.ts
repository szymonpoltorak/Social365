import { Component, Input, OnInit } from '@angular/core';
import { AsyncPipe } from "@angular/common";
import { MatAutocomplete, MatAutocompleteTrigger, MatOption } from "@angular/material/autocomplete";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { MatMiniFabButton } from "@angular/material/button";
import { MatTooltip } from "@angular/material/tooltip";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { map, Observable, startWith } from "rxjs";
import { MatTabsModule } from "@angular/material/tabs";
import { MatBadge } from "@angular/material/badge";
import { RouterPaths } from "@enums/router-paths.enum";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { User } from "@interfaces/feed/user.interface";

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
        MatMenuTrigger,
        MatTabsModule,
        MatBadge
    ],
    templateUrl: './toolbar.component.html',
    styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent implements OnInit {
    protected readonly searchSocialControl: FormControl<string> = new FormControl();
    protected user !: User;
    protected readonly RouterPaths = RouterPaths;
    @Input() isOnFeed!: boolean;
    options: string[] = ['Szymon Półtorak', 'Jacek Kowalski', 'John Smith', "Stefan Nowak"];
    filteredOptions !: Observable<string[]>;
    newMessages: number = 5;
    newNotifications: number = 0;

    constructor(protected router: Router,
                private localStorageService: LocalStorageService) {
    }

    ngOnInit(): void {
        this.filteredOptions = this.searchSocialControl.valueChanges.pipe(
            startWith(''),
            map(value => this._filter(value || '')),
        );
        this.user = this.localStorageService.getUserFromStorage();
    }

    private _filter(value: string): string[] {
        const filterValue: string = value.toLowerCase();

        return this.options.filter(option => option.toLowerCase().includes(filterValue));
    }
}
