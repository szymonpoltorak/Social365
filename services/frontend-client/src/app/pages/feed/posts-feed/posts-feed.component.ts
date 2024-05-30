import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { MatCard, MatCardActions } from "@angular/material/card";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
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
import { NgIf, NgOptimizedImage, NgStyle } from "@angular/common";
import { User } from "@interfaces/feed/user.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { EmojiEvent } from "@ctrl/ngx-emoji-mart/ngx-emoji";
import { Either } from "@core/types/feed/either.type";
import { SharedPost } from '@core/interfaces/feed/shared-post.interface';
import { SharedPostComponent } from "@pages/feed/posts-feed/shared-post/shared-post.component";
import { MatMenu, MatMenuItem } from "@angular/material/menu";

interface MyFile {
    fileUrl: string;
    file: File;
}

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
        NgStyle,
        NgIf,
        MatIconButton,
        MatMenu,
        MatMenuItem
    ],
    templateUrl: './posts-feed.component.html',
    styleUrl: './posts-feed.component.scss'
})
export class PostsFeedComponent implements OnInit {
    protected currentUser !: User;
    protected contentControl: FormControl<string | null> = new FormControl<string>("", []);
    @Input() posts !: Either<Post, SharedPost>[];
    @ViewChild('fileInput', { static: false }) fileInput!: ElementRef;
    isOpened: boolean = false;
    isAttachingImagesOpened: boolean = true;
    allowedFileTypes: string[] = [
        'image/jpeg',
        'image/png',
    ];
    isUploading = false;
    myFiles: MyFile[] = [];

    constructor(private localStorage: LocalStorageService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserFromStorage();
    }

    //TODO: implement file type validation
    handleChange(event: any): void {
        const files: File[] = event.target.files as File[];

        for (const file of files) {
            this.myFiles.push({
                fileUrl: URL.createObjectURL(file),
                file: file,
            });
        }
        this.isAttachingImagesOpened = false;
    }

    handleRemovesFile(fileToRemove: MyFile): void {
        if (this.fileInput && this.fileInput.nativeElement) {
            this.fileInput.nativeElement.value = null;
        }
        this.myFiles = this.myFiles.filter((file) => file !== fileToRemove);
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
