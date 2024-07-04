import { Component, OnInit } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { ImagesService } from "@api/images/images.service";
import { RoutingService } from "@services/profile/routing.service";
import { PostImage } from "@interfaces/images/post-image.interface";
import { Page } from "@interfaces/utils/page.interface";

@Component({
    selector: 'app-profile-photos',
    standalone: true,
    imports: [
        MatCard,
        MatCardHeader,
        MatCardTitle,
        MatCardContent,
        MatIconButton,
        MatIcon,
        MatMenu,
        MatMenuTrigger,
        MatMenuItem
    ],
    templateUrl: './profile-photos.component.html',
    styleUrl: './profile-photos.component.scss'
})
export class ProfilePhotosComponent implements OnInit {

    private readonly FIRST_PAGE: number = 0;
    private readonly PAGE_SIZE: number = 15;
    protected photos !: Page<PostImage>;
    protected currentUsername !: string;

    constructor(private imagesService: ImagesService,
                private routingService: RoutingService) {
    }

    ngOnInit(): void {
        this.currentUsername = this.routingService.getCurrentUsernameForRoute();

        this.imagesService
            .getUserUploadedImages(this.currentUsername, this.FIRST_PAGE, this.PAGE_SIZE)
            .subscribe((images: Page<PostImage>) => {
                console.log(images);

                this.photos = images;

                window.scrollTo(0, 0);
            });
    }

    handleDownloadImage(imagePath: string): void {
        this.imagesService
            .downloadImage(imagePath)
            .subscribe((blob: Blob) => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');

                a.href = url;
                a.download = imagePath;
                a.click();

                document.body.removeChild(a);
                window.URL.revokeObjectURL(url);
            });
    }
}
