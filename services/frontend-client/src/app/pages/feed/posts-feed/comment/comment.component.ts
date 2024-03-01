import { Component, Input } from '@angular/core';
import { PostComment } from "@core/data/feed/PostComment";
import { AvatarPhotoComponent } from "@shared/avatar-photo/avatar-photo.component";
import { MatButton } from "@angular/material/button";
import { PostAgePipe } from "@core/pipes/post-age.pipe";

@Component({
  selector: 'app-comment',
  standalone: true,
  imports: [
    AvatarPhotoComponent,
    MatButton,
    PostAgePipe
  ],
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.scss'
})
export class CommentComponent {
  @Input() comment!: PostComment;

}
