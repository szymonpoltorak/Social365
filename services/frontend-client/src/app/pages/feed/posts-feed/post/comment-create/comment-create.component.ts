import { Component, Input } from '@angular/core';
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { User } from "@core/data/feed/User";
import { EmojiEvent } from "@ctrl/ngx-emoji-mart/ngx-emoji";

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
    protected contentControl: FormControl<string | null> = new FormControl<string>("", []);
    @Input() user !: User;
    isOpened: boolean = false;

    emojiSelected($event: EmojiEvent): void {
        if ($event.emoji.native === undefined || $event.emoji.native === null) {
            return;
        }
        this.contentControl.setValue(this.contentControl.value + $event.emoji.native);
        this.isOpened = !this.isOpened;
    }
}
