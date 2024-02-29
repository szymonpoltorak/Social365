import { Component, Input } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatDivider } from "@angular/material/divider";
import { BirthdayInfo } from "@core/data/feed/BirthdayInfo";
import { MatAutocompleteTrigger } from "@angular/material/autocomplete";
import { MatInput } from "@angular/material/input";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";

@Component({
    selector: 'app-birthday',
    standalone: true,
    imports: [
        MatButton,
        MatIcon,
        MatDivider,
        MatAutocompleteTrigger,
        MatInput,
        ReactiveFormsModule,
        FormsModule
    ],
    templateUrl: './birthday.component.html',
    styleUrl: './birthday.component.scss'
})
export class BirthdayComponent {
    @Input() birthdayInfos !: BirthdayInfo[];
    protected isExpanded: boolean = false;
    birthdayFormControl: FormControl<string | null> = new FormControl<string>("", []);

    createBirthdayPost(): void {
        if (this.birthdayFormControl.value?.length === 0) {
            return;
        }
        console.log('Create birthday post');
        console.log(this.birthdayFormControl.value);

        this.birthdayFormControl.reset();
    }
}
