import { Component, OnInit } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from "@angular/material/card";
import { MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { ImagesService } from "@api/images/images.service";
import { RoutingService } from "@services/utils/routing.service";
import { PostImage } from "@interfaces/images/post-image.interface";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { PageablePagingState } from "@core/utils/pageable-paging-state";
import { SocialPage } from "@core/utils/social-page";

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
        MatMenuItem,
        MatProgressSpinner
    ],
    templateUrl: './profile-photos.component.html',
    styleUrl: './profile-photos.component.scss'
})
export class ProfilePhotosComponent implements OnInit {

    protected photos !: SocialPage<PostImage, PageablePagingState>;
    protected currentUsername !: string;
    private readonly PAGE_SIZE: number = 15;

    constructor(private imagesService: ImagesService,
                private routingService: RoutingService) {
    }

    ngOnInit(): void {
        this.currentUsername = this.routingService.getCurrentUsernameForRoute();

        this.imagesService
            .getUserUploadedImages(this.currentUsername, PageablePagingState.firstPage(this.PAGE_SIZE))
            .subscribe((images: SocialPage<PostImage, PageablePagingState>) => {
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
