import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatCard } from "@angular/material/card";
import { MatDivider } from "@angular/material/divider";
import { MatFormField, MatHint, MatLabel, MatSuffix } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { EmojiEvent } from "@ctrl/ngx-emoji-mart/ngx-emoji";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { Profile } from "@interfaces/feed/profile.interface";
import { DropImageComponent } from "@shared/drop-image/drop-image.component";
import { Post } from "@interfaces/feed/post.interface";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { PostService } from "@api/posts-comments/post.service";
import { ImagesService } from "@api/images/images.service";

@Component({
    selector: 'app-post-create-header',
    standalone: true,
    imports: [
        AvatarPhotoComponent,
        CdkTextareaAutosize,
        FormsModule,
        MatButton,
        MatCard,
        MatDivider,
        MatFormField,
        MatHint,
        MatIcon,
        MatIconButton,
        MatInput,
        MatLabel,
        MatSuffix,
        PickerComponent,
        ReactiveFormsModule,
        DropImageComponent
    ],
    templateUrl: './post-create-header.component.html',
    styleUrl: './post-create-header.component.scss'
})
export class PostCreateHeaderComponent implements OnInit {

    @ViewChild(DropImageComponent) dropImageComponent !: DropImageComponent;
    @Output() postCreated: EventEmitter<Post> = new EventEmitter<Post>();
    @Input() isSharedPost: boolean = false;
    protected isAttachingImagesOpened: boolean = false;
    protected attachedImagesLength: number = 0;
    protected contentControl: FormControl<string | null> = new FormControl<string>("", []);
    protected currentUser !: Profile;
    protected isOpened: boolean = false;

    constructor(private localStorage: LocalStorageService,
                private imagesService: ImagesService,
                private postService: PostService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();
    }

    emojiSelected($event: EmojiEvent): void {
        if ($event.emoji.native === undefined || $event.emoji.native === null) {
            return;
        }
        this.contentControl.setValue(this.contentControl.value + $event.emoji.native);

        this.isOpened = !this.isOpened;
    }

    createNewPost(): void {
        const postContent: string = this.contentControl.value || "";
        const hasAttachments: boolean = this.attachedImagesLength > 0;

        this.postService
            .createPost(this.currentUser.profileId, postContent, hasAttachments)
            .subscribe((post: Post) => {
                const images: AttachImage[] = this.dropImageComponent.onFormSubmit();

                post.imageUrls = images.map((image: AttachImage) => image.fileUrl);

                images.forEach((image: AttachImage) => {
                    this.imagesService.uploadPostImage(this.currentUser.username, image, post.postId).subscribe();
                });

                this.postCreated.emit(post);

                this.contentControl.setValue("");
            });
    }

}
