import { Component, Input } from '@angular/core';
import { SharedPost } from "@interfaces/feed/shared-post.interface";
import { Either } from "@core/types/feed/either.type";
import { Post } from "@interfaces/feed/post.interface";

@Component({
    selector: 'app-shared-post',
    standalone: true,
    imports: [],
    templateUrl: './shared-post.component.html',
    styleUrl: './shared-post.component.scss'
})
export class SharedPostComponent {
    @Input({ transform: (value: Either<Post, SharedPost>) => value as SharedPost })
    post !: SharedPost;
}
