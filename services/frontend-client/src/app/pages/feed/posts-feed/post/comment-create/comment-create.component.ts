import { Component, Input } from '@angular/core';
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { Author } from "@core/data/feed/Author";

@Component({
    selector: 'app-comment-create',
    standalone: true,
    imports: [
        AvatarPhotoComponent,
        CdkTextareaAutosize,
        FormsModule,
        MatButton,
        MatFormField,
        MatHint,
        MatIcon,
        MatIconButton,
        MatInput,
        MatLabel,
        PickerComponent,
        ReactiveFormsModule
    ],
    templateUrl: './comment-create.component.html',
    styleUrl: './comment-create.component.scss'
})
export class CommentCreateComponent {
    protected contentControl: FormControl<any> = new FormControl<string>("", []);
    @Input() user !: Author;
    isOpened: boolean = false;

    emojiSelected($event: any): void {
        this.contentControl.setValue(this.contentControl.value + $event.emoji.native);
        this.isOpened = !this.isOpened;
    }
}
