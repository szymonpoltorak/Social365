import { Component, OnInit } from '@angular/core';
import { MatDialogModule } from "@angular/material/dialog";
import { MatButton, MatIconButton } from "@angular/material/button";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatFormField, MatHint, MatLabel, MatSuffix } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { Profile } from "@interfaces/feed/profile.interface";
import { MatIcon } from "@angular/material/icon";

@Component({
    selector: 'app-create-share-post-dialog',
    standalone: true,
    imports: [
        MatDialogModule,
        MatButton,
        AvatarPhotoComponent,
        CdkTextareaAutosize,
        FormsModule,
        MatFormField,
        MatHint,
        MatInput,
        MatLabel,
        ReactiveFormsModule,
        MatIcon,
        MatSuffix,
        MatIconButton,
    ],
    templateUrl: './create-share-post-dialog.component.html',
    styleUrl: './create-share-post-dialog.component.scss'
})
export class CreateSharePostDialogComponent implements OnInit {
    currentUser !: Profile;
    protected contentControl: FormControl<string | null> = new FormControl<string>("", []);

    constructor(private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();
    }
}
