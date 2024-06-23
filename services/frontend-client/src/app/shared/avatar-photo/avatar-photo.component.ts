import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-avatar-photo',
    standalone: true,
    imports: [],
    templateUrl: './avatar-photo.component.html',
    styleUrl: './avatar-photo.component.scss'
})
export class AvatarPhotoComponent {
    @Input() imageLink !: string;
}
