import { Component, Input } from '@angular/core';
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { User } from "@interfaces/feed/user.interface";
import { EmojiEvent } from "@ctrl/ngx-emoji-mart/ngx-emoji";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from "@angular/material/input";
import { DropImageComponent } from "@shared/drop-image/drop-image.component";

@Component({
    selector: 'app-comment-create',
    standalone: true,
    imports: [
        AvatarPhotoComponent,
        CdkTextareaAutosize,
        FormsModule,
        MatFormFieldModule,
        MatIconModule,
        MatButtonModule,
        MatInputModule,
        PickerComponent,
        ReactiveFormsModule,
        DropImageComponent
    ],
    templateUrl: './comment-create.component.html',
    styleUrl: './comment-create.component.scss'
})
export class CommentCreateComponent {
    protected contentControl: FormControl<string | null> = new FormControl<string>("", []);
    @Input() user !: User;
    isOpened: boolean = false;
    isAttachingImagesOpened: boolean = false;
    attachedImagesLength: number = 0;

    emojiSelected($event: EmojiEvent): void {
        if ($event.emoji.native === undefined || $event.emoji.native === null) {
            return;
        }
        this.contentControl.setValue(this.contentControl.value + $event.emoji.native);
        this.isOpened = !this.isOpened;
    }
}
