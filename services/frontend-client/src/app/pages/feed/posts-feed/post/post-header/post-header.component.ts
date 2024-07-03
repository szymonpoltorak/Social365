import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { DatePipe } from "@angular/common";
import { MatCardSubtitle, MatCardTitle } from "@angular/material/card";
import { MatIcon } from "@angular/material/icon";
import { MatIconButton } from "@angular/material/button";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { MatTooltip } from "@angular/material/tooltip";
import { PostAgePipe } from "@core/pipes/post-age.pipe";
import { Post } from "@interfaces/feed/post.interface";
import { MatDialog } from "@angular/material/dialog";
import { PostEditDialogComponent } from "@pages/feed/posts-feed/post-edit-dialog/post-edit-dialog.component";

@Component({
    selector: 'app-post-header',
    standalone: true,
    imports: [
        AvatarPhotoComponent,
        DatePipe,
        MatCardSubtitle,
        MatCardTitle,
        MatIcon,
        MatIconButton,
        MatMenu,
        MatMenuItem,
        MatTooltip,
        PostAgePipe,
        MatMenuTrigger
    ],
    templateUrl: './post-header.component.html',
    styleUrl: './post-header.component.scss'
})
export class PostHeaderComponent implements OnInit {
    @Input() post !: Post;
    @Input() username !: string;
    @Input() isSharedPost: boolean = false;
    @Output() deletePostEvent: EventEmitter<void> = new EventEmitter<void>();
    creationDateTime !: Date;

    constructor(public dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.creationDateTime = new Date(this.post.creationDateTime);
    }

    editPost(): void {
        const dialogRef = this.dialog.open(PostEditDialogComponent, {
            minHeight: '430px',
            width: '530px',
            data: {
                post: this.post,
                isSharedPost: this.isSharedPost
            }
        });
    }
}
