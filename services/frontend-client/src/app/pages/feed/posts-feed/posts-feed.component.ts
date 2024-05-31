import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { MatCard, MatCardActions } from "@angular/material/card";
import { MatFormField, MatHint, MatLabel, MatSuffix } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatDivider } from "@angular/material/divider";
import { MatButton, MatFabButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { PostComponent } from "@pages/feed/posts-feed/post/post.component";
import { Post } from "@interfaces/feed/post.interface";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { NgOptimizedImage } from "@angular/common";
import { User } from "@interfaces/feed/user.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { EmojiEvent } from "@ctrl/ngx-emoji-mart/ngx-emoji";
import { Either } from "@core/types/feed/either.type";
import { SharedPost } from '@core/interfaces/feed/shared-post.interface';
import { SharedPostComponent } from "@pages/feed/posts-feed/shared-post/shared-post.component";
import { MatMenu, MatMenuItem } from "@angular/material/menu";
import { MatSnackBar } from "@angular/material/snack-bar";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { DropImageComponent } from "@shared/drop-image/drop-image.component";


@Component({
    selector: 'app-posts-feed',
    standalone: true,
    imports: [
        MatCard,
        MatFormField,
        MatInput,
        MatLabel,
        MatDivider,
        MatIcon,
        MatFabButton,
        PostComponent,
        MatButton,
        MatCardActions,
        CdkTextareaAutosize,
        MatHint,
        ReactiveFormsModule,
        PickerComponent,
        NgOptimizedImage,
        AvatarPhotoComponent,
        SharedPostComponent,
        MatIconButton,
        MatMenu,
        MatMenuItem,
        MatSuffix,
        DropImageComponent
    ],
    templateUrl: './posts-feed.component.html',
    styleUrl: './posts-feed.component.scss'
})
export class PostsFeedComponent implements OnInit {
    protected currentUser !: User;
    protected contentControl: FormControl<string | null> = new FormControl<string>("", []);
    @Input() posts !: Either<Post, SharedPost>[];
    isOpened: boolean = false;
    isAttachingImagesOpened: boolean = false;
    attachedImagesLength: number = 0;

    constructor(private localStorage: LocalStorageService,
                private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserFromStorage();
    }

    emojiSelected($event: EmojiEvent): void {
        if ($event.emoji.native === undefined || $event.emoji.native === null) {
            return;
        }
        this.contentControl.setValue(this.contentControl.value + $event.emoji.native);

        this.isOpened = !this.isOpened;
    }

    isPost(post: Either<Post, SharedPost>): boolean {
        return Object.prototype.hasOwnProperty.call(post, "postId");
    }
}
