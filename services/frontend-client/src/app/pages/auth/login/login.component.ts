import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from "@angular/material/input";
import { MatIconModule } from "@angular/material/icon";
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { NgIf } from "@angular/common";
import { RouterLink } from "@angular/router";

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [
        MatButtonModule,
        MatInputModule,
        MatIconModule,
        ReactiveFormsModule,
        NgIf,
        RouterLink
    ],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
    loginForm !: FormGroup;

    constructor(private formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        this.loginForm = this.formBuilder.group({
            email: new FormControl<string>("", [
                Validators.required, Validators.email, Validators.minLength(3), Validators.maxLength(100)
            ]),
            password: new FormControl<string>("", [
                Validators.required, Validators.minLength(6), Validators.maxLength(100),
                Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[:\\?\\.@!#:\\-_=+ ])[a-zA-Z0-9:\\?\\.@!#:\\-_=+ ]{8,32}$")
            ])
        });
    }

    submitForm(): void {
        if (this.loginForm.invalid) {
            return;
        }
    }
}
