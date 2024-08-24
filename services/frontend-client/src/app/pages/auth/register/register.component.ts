import { Component, OnInit } from '@angular/core';
import { MatButton, MatFabButton } from "@angular/material/button";
import { MatError, MatFormField } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { NgIf } from "@angular/common";
import {
    AbstractControl,
    FormBuilder,
    FormControl,
    FormGroup,
    ReactiveFormsModule,
    ValidatorFn,
    Validators
} from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { RegisterRequest } from "@interfaces/auth/register-request.interface";
import { LocalStorageService } from '@core/services/utils/local-storage.service';
import { AuthService } from "@api/auth/auth.service";
import { RouterPaths } from "@enums/router-paths.enum";
import { Auth } from "@interfaces/auth/auth-response.interface";

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
    passwordsGroup !: FormGroup;
    private readonly PASSWORD_PATTERN: string = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[:\\?\\.@!#:\\-_=+ ])[a-zA-Z0-9:\\?\\.@!#:\\-_=+ ]{8,32}$";

    constructor(private formBuilder: FormBuilder,
                private authService: AuthService,
                private localStorage: LocalStorageService,
                private router: Router) {
    }

    passwordMatchValidator(passwordFieldName: string, repeatFieldName: string): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const userPassword: AbstractControl<any, any> | null = control.get(passwordFieldName);
            const repeatPassword: AbstractControl<any, any> | null = control.get(repeatFieldName);

            return userPassword && repeatPassword && userPassword.value !== repeatPassword.value ? { 'passwordMatch': true } : null;
        };
    }

    properAgeValidator(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const currentDateYear: number = new Date().getFullYear();
            const dateFieldValueYear: number = new Date(control.value).getFullYear();
            const userAge: number = currentDateYear - dateFieldValueYear;
            const maxAge: number = 200;
            const minAge: number = 13;
            const condition: boolean = userAge > maxAge || userAge < minAge || dateFieldValueYear > currentDateYear;

            return condition ? { 'wrongAge': true } : null;
        };
    }

    ngOnInit(): void {
        this.passwordsGroup = this.formBuilder.group({
            password: new FormControl<string>("", [
                Validators.required, Validators.minLength(6), Validators.maxLength(100),
                Validators.pattern(this.PASSWORD_PATTERN)
            ]),
            confirmPassword: new FormControl<string>("", [
                Validators.required, Validators.minLength(6), Validators.maxLength(100),
                Validators.pattern(this.PASSWORD_PATTERN)
            ]),
        }, {
            validators: [
                this.passwordMatchValidator("password", "confirmPassword"),
            ]
        });

        this.registerForm = this.formBuilder.group({
            firstName: new FormControl<string>("", [
                Validators.required, Validators.minLength(2), Validators.maxLength(32)
            ]),
            lastName: new FormControl<string>("", [
                Validators.required, Validators.minLength(2), Validators.maxLength(32)
            ]),
            email: new FormControl<string>("", [
                Validators.required, Validators.email, Validators.minLength(3), Validators.maxLength(100)
            ]),
            passwords: this.passwordsGroup,
            dateOfBirth: new FormControl<string>("", [
                Validators.required, this.properAgeValidator()
            ]),
        });
    }

    submitForm(): void {
        if (this.registerForm.invalid) {
            return;
        }
        const request: RegisterRequest = {
            username: this.registerForm.get('email')?.value,
            password: this.passwordsGroup.get('password')?.value,
            dateOfBirth: this.registerForm.get('dateOfBirth')?.value,
            firstName: this.registerForm.get('firstName')?.value,
            lastName: this.registerForm.get('lastName')?.value,
        };

        this.authService
            .registerUser(request)
            .subscribe((response: Auth) => {
                this.localStorage.saveUserToStorage(response.profile);
                this.localStorage.saveTokenToStorage(response.token);

                this.router.navigate([RouterPaths.FEED_DIRECT])
            });
    }
}
