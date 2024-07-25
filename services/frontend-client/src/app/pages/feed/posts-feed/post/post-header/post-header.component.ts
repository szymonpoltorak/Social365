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
import { take } from "rxjs";
import { EditDialogOutput } from "@interfaces/posts-comments/edit-dialog-output.interface";
import { RoutingService } from "@services/profile/routing.service";

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
    @Output() editPostEvent: EventEmitter<EditDialogOutput> = new EventEmitter<EditDialogOutput>();
    creationDateTime !: Date;

    constructor(public dialog: MatDialog,
                protected routingService: RoutingService) {
    }

    ngOnInit(): void {
        this.creationDateTime = new Date(this.post.postKey.creationDateTime);
    }

    editPost(): void {
        const dialogRef = this.dialog.open(PostEditDialogComponent, {
            minHeight: '300px',
            width: '530px',
            data: {
                post: this.post,
                isSharedPost: this.isSharedPost
            },
            exitAnimationDuration: 100
        });

        dialogRef
            .afterClosed()
            .pipe(take(1))
            .subscribe((result: EditDialogOutput) => {
                this.editPostEvent.emit(result);
            });
    }
}
