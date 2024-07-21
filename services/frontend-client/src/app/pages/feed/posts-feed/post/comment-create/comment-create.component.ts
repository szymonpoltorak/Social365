import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { Profile } from "@interfaces/feed/profile.interface";
import { EmojiEvent } from "@ctrl/ngx-emoji-mart/ngx-emoji";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from "@angular/material/input";
import { DropImageComponent } from "@shared/drop-image/drop-image.component";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { MatSnackBar } from '@angular/material/snack-bar';
import { Optional } from "@core/types/profile/optional.type";
import { CommentCreateData } from "@interfaces/posts-comments/comment-create-data.interface";
import { ReplyComment } from "@interfaces/posts-comments/reply-comment.interface";
import { PostComment } from "@interfaces/posts-comments/post-comment.interface";
import { Either } from "@core/types/feed/either.type";

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
export class CommentCreateComponent implements OnInit {
    @Input() user !: Profile;
    @Input() comment!: Either<PostComment, ReplyComment>;
    @Output() commentCreated: EventEmitter<CommentCreateData> = new EventEmitter<CommentCreateData>();
    isOpened: boolean = false;
    allowedFileTypes: string[] = [
        'image/jpeg',
        'image/png',
    ];
    attachedImage: Optional<AttachImage> = null;
    protected contentControl !: FormControl<string | null>;

    constructor(private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {
        const startVal: string = this.comment === undefined ? "" : this.comment.content || "";

        this.contentControl = new FormControl<string>(startVal, []);
    }

    emojiSelected($event: EmojiEvent): void {
        if ($event.emoji.native === undefined || $event.emoji.native === null) {
            return;
        }
        this.contentControl.setValue(this.contentControl.value + $event.emoji.native);
        this.isOpened = !this.isOpened;
    }

    handleChange(event: any): void {
        const file: File = event.target.files[0] as File;

        if (this.allowedFileTypes.indexOf(file.type) === -1) {
            this.snackBar.open(`Invalid file type for file named ${ file.name }`, 'Close', {
                duration: 2000,
            });

            return;
        }
        this.attachedImage = {
            fileUrl: URL.createObjectURL(file),
            file: file,
        };

        this.snackBar.open(`Successfully attached 1 images!`, 'Close', {
            duration: 2000
        });
    }

    handleRemovesFile(): void {
        this.attachedImage = null;
        this.snackBar.open(`Successfully removed 1 images!`, 'Close', {
            duration: 2000
        });
    }

    emitCommitCreatedEvent(): void {
        const data: CommentCreateData = {
            content: this.contentControl.value || "",
            attachedImage: this.attachedImage
        };
        this.contentControl.reset();
        this.attachedImage = null;

        this.commentCreated.emit(data);
    }
}
