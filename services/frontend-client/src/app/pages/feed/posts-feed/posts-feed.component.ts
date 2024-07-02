import { Component, HostListener, Input, OnInit, ViewChild } from '@angular/core';
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
import { FormControl, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { NgOptimizedImage } from "@angular/common";
import { Profile } from "@interfaces/feed/profile.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { EmojiEvent } from "@ctrl/ngx-emoji-mart/ngx-emoji";
import { Either } from "@core/types/feed/either.type";
import { SharedPost } from '@core/interfaces/feed/shared-post.interface';
import { SharedPostComponent } from "@pages/feed/posts-feed/shared-post/shared-post.component";
import { MatMenu, MatMenuItem } from "@angular/material/menu";
import { DropImageComponent } from "@shared/drop-image/drop-image.component";
import { PostService } from "@api/posts-comments/post.service";
import { Optional } from "@core/types/profile/optional.type";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { PostMappings } from "@enums/api/posts-comments/post-mappings.enum";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ImagesService } from "@api/images/images.service";
import { AttachImage } from "@interfaces/feed/attach-image.interface";


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
        DropImageComponent,
        FormsModule
    ],
    templateUrl: './posts-feed.component.html',
    styleUrl: './posts-feed.component.scss'
})
export class PostsFeedComponent implements OnInit {

    @Input() presentedProfileId !: string;
    @Input() postsUrl !: PostMappings;
    @ViewChild(DropImageComponent) dropImageComponent !: DropImageComponent;
    private readonly FIRST_PAGE: number = 0;
    private readonly PAGE_SIZE: number = 20;
    private pagingState: Optional<string> = null;
    protected posts !: CassandraPage<Either<Post, SharedPost>>;
    protected isOpened: boolean = false;
    protected isAttachingImagesOpened: boolean = false;
    protected attachedImagesLength: number = 0;
    protected currentUser !: Profile;
    protected contentControl: FormControl<string | null> = new FormControl<string>("", []);

    constructor(private localStorage: LocalStorageService,
                private snackbar: MatSnackBar,
                private imagesService: ImagesService,
                private postService: PostService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();

        this.postService
            .getPostsFromUrl(this.presentedProfileId, this.FIRST_PAGE, this.PAGE_SIZE, this.pagingState, this.postsUrl)
            .subscribe((posts: CassandraPage<Either<Post, SharedPost>>) => {
                this.posts = posts;

                window.scrollTo(0, 0);
            });
    }

    @HostListener('window:scroll', ['$event'])
    onScroll(event: any): void {
        if (!this.posts.hasNextPage) {
            return;
        }
        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position < max) {
            return;
        }
        this.postService
            .getPostsFromUrl(this.presentedProfileId,  this.posts.friendsPageNumber + 1,
                this.PAGE_SIZE, this.posts.pagingState, this.postsUrl)
            .subscribe((posts: CassandraPage<Either<Post, SharedPost>>) => {
                const oldContent: Either<Post, SharedPost>[] = this.posts.data;

                this.posts = posts;

                this.posts.data = oldContent.concat(posts.data);
            });
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

                this.posts.data.unshift(post);

                this.snackbar.open("Successfully created post!", "Close",{
                    duration: 2000,
                });

                this.contentControl.setValue("");
            });
    }
}
