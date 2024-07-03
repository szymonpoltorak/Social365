import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatButton, MatIconButton } from "@angular/material/button";
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogRef } from "@angular/material/dialog";
import { MatIcon } from "@angular/material/icon";
import { PostCreateHeaderComponent } from "@pages/feed/posts-feed/post-create-header/post-create-header.component";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { MatCard } from "@angular/material/card";
import { MatDivider } from "@angular/material/divider";
import { MatFormField, MatHint, MatLabel, MatSuffix } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { FormControl, ReactiveFormsModule, Validators } from "@angular/forms";
import { DropImageComponent } from "@shared/drop-image/drop-image.component";
import { Profile } from "@interfaces/feed/profile.interface";
import { EmojiEvent } from "@ctrl/ngx-emoji-mart/ngx-emoji";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { EditDialogData } from "@interfaces/posts-comments/edit-dialog-data.interface";
import { NgClass } from "@angular/common";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { Post } from "@interfaces/feed/post.interface";

@Component({
    selector: 'app-post-edit-dialog',
    standalone: true,
    imports: [
        MatButton,
        MatDialogActions,
        MatDialogClose,
        MatIcon,
        PostCreateHeaderComponent,
        AvatarPhotoComponent,
        CdkTextareaAutosize,
        MatCard,
        MatDivider,
        MatFormField,
        MatHint,
        MatIconButton,
        MatInput,
        MatLabel,
        MatSuffix,
        PickerComponent,
        ReactiveFormsModule,
        DropImageComponent,
        NgClass
    ],
    templateUrl: './post-edit-dialog.component.html',
    styleUrl: './post-edit-dialog.component.scss'
})
export class PostEditDialogComponent implements OnInit {

    @ViewChild(DropImageComponent) dropImageComponent !: DropImageComponent;
    protected isAttachingImagesOpened: boolean = false;
    protected attachedImagesLength: number = 0;
    protected contentControl !: FormControl<string | null>;
    protected currentUser !: Profile;
    protected isOpened: boolean = false;

    constructor(private localStorage: LocalStorageService,
                @Inject(MAT_DIALOG_DATA) public data: EditDialogData,
                private dialogRef: MatDialogRef<PostEditDialogComponent>) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();
        this.contentControl = new FormControl<string>(this.data.post.content, [
            Validators.maxLength(1000)
        ]);
    }

    emojiSelected($event: EmojiEvent): void {
        if ($event.emoji.native === undefined || $event.emoji.native === null) {
            return;
        }
        this.contentControl.setValue(this.contentControl.value + $event.emoji.native);

        this.isOpened = !this.isOpened;
    }

    closeDialog(): void {
        const newUrls: AttachImage[] = this.dropImageComponent.onFormSubmit();
        //TODO: Proper filtering to get added, deleted and existing images
        // const deleteUrls: string[] = this.data.post.imageUrls.filter((file: string) => !newUrls.includes(file));
        // const deleteImages: string[] = newUrls.filter((file: AttachImage) => );
        // const addedImages: AttachImage[] = newUrls.filter((file: AttachImage) => !this.data.post.imageUrls.includes(file.fileUrl));

        this.dialogRef.close();
    }

}
