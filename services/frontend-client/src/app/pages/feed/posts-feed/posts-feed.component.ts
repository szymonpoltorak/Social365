import { Component, HostListener, Input, OnInit } from '@angular/core';
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
import { Page } from "@interfaces/utils/page.interface";
import { ProfileSearch } from "@interfaces/search/profile-search.interface";


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

    @Input() presentedProfileId !: string;
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
                private postService: PostService) {
    }

    ngOnInit(): void {
        this.currentUser = this.localStorage.getUserProfileFromStorage();

        this.postService
            .getPostsOnPage(this.presentedProfileId, this.FIRST_PAGE, this.PAGE_SIZE, this.pagingState)
            .subscribe((posts: CassandraPage<Either<Post, SharedPost>>) => {
                this.posts = posts;
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
            .getPostsOnPage(this.presentedProfileId,  this.posts.friendsPageNumber + 1, this.PAGE_SIZE, this.posts.pagingState)
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
}
