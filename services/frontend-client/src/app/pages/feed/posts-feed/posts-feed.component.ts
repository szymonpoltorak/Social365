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
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { PickerComponent } from "@ctrl/ngx-emoji-mart";
import { NgOptimizedImage } from "@angular/common";
import { Profile } from "@interfaces/feed/profile.interface";
import { LocalStorageService } from "@services/utils/local-storage.service";
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
import { SharePostData } from "@interfaces/posts-comments/share-post-data.interface";
import { PostCreateHeaderComponent } from "@pages/feed/posts-feed/post-create-header/post-create-header.component";
import { MatProgressSpinner } from "@angular/material/progress-spinner";


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
        FormsModule,
        PostCreateHeaderComponent,
        MatProgressSpinner
    ],
    templateUrl: './posts-feed.component.html',
    styleUrl: './posts-feed.component.scss'
})
export class PostsFeedComponent implements OnInit {

    @Input() presentedProfileId !: string;
    @Input() postsUrl !: PostMappings;
    protected posts !: CassandraPage<Either<Post, SharedPost>>;
    protected currentUser !: Profile;
    private readonly FIRST_PAGE: number = 0;
    private readonly PAGE_SIZE: number = 20;
    private pagingState: Optional<string> = null;

    constructor(private localStorage: LocalStorageService,
                private snackbar: MatSnackBar,
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
    onScroll(): void {
        if (!this.posts.hasNextPage) {
            return;
        }
        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position < max) {
            return;
        }
        this.postService
            .getPostsFromUrl(this.presentedProfileId, this.posts.friendsPageNumber + 1,
                this.PAGE_SIZE, this.posts.pagingState, this.postsUrl)
            .subscribe((posts: CassandraPage<Either<Post, SharedPost>>) => {
                const oldContent: Either<Post, SharedPost>[] = this.posts.data;

                this.posts = posts;

                this.posts.data = oldContent.concat(posts.data);
            });
    }

    isPost(post: Either<Post, SharedPost>): boolean {
        return !Object.prototype.hasOwnProperty.call(post, "sharingPost");
    }

    sharePost(event: SharePostData): void {
        this.postService
            .sharePost(event.post.postKey, event.content)
            .subscribe((sharedPost: SharedPost) => {
                this.posts.data.unshift(sharedPost);

                this.snackbar.open("Successfully shared post!", "Close", {
                    duration: 2000,
                });
            });

    }

    deletePost(event: Post): void {
        this.postService
            .deletePost(event.postKey.postId, event.postKey.author.profileId)
            .subscribe(() => {
                this.posts.data = this.posts.data.filter((post: Either<Post, SharedPost>) => post !== event);

                this.snackbar.open("Successfully deleted post!", "Close", {
                    duration: 2000,
                });
            });
    }

    createdPost(event: Post): void {
        this.posts.data.unshift(event);

        this.snackbar.open("Successfully created post!", "Close", {
            duration: 2000,
        });
    }

}
