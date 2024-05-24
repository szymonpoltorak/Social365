import { Component, OnInit } from '@angular/core';
import { MatButton, MatFabButton } from "@angular/material/button";
import { MatError, MatFormField } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { NgIf } from "@angular/common";
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { RouterLink } from "@angular/router";
import { MatDatepickerModule } from "@angular/material/datepicker";

@Component({
    selector: 'app-register',
    standalone: true,
    imports: [
        MatButton,
        MatError,
        MatFabButton,
        MatFormField,
        MatIcon,
        MatInputModule,
        NgIf,
        ReactiveFormsModule,
        RouterLink,
        MatDatepickerModule,
    ],
    templateUrl: './register.component.html',
    styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit {
    registerForm !: FormGroup;

    constructor(private formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        this.registerForm = this.formBuilder.group({
            email: new FormControl<string>("", [
                Validators.required, Validators.email, Validators.minLength(3), Validators.maxLength(100)
            ]),
            passwords: {
                password: new FormControl<string>("", [
                    Validators.required, Validators.minLength(6), Validators.maxLength(100),
                    Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[:\\?\\.@!#:\\-_=+ ])[a-zA-Z0-9:\\?\\.@!#:\\-_=+ ]{8,32}$")
                ]),
                confirmPassword: new FormControl<string>("", [])
            },
        });
    }

    submitForm(): void {

    }
}
