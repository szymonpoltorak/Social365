import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { ImagesMappings } from "@enums/api/images/images-mappings.enum";
import { map, Observable, take } from "rxjs";
import { PostImage } from "@interfaces/images/post-image.interface";
import { Image } from "@interfaces/images/image.interface";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";
import { ImageType } from "@enums/api/images/image-type.enum";

@Injectable({
    providedIn: 'root'
})
export class ImagesService {

    constructor(private http: HttpClient) {
    }

    uploadPostImage(image: AttachImage, postId: string): Observable<void> {
        return this.http.post<void>(ImagesMappings.UPLOAD_POST_IMAGE_MAPPING, this.toFormData(image), {
            params: {
                postId: postId
            }
        }).pipe(take(1));
    }

    uploadCommentImage(image: AttachImage, commentId: string): Observable<void> {
        return this.http.post<void>(ImagesMappings.UPLOAD_COMMENT_IMAGE_MAPPING, this.toFormData(image), {
            params: {
                commentId: commentId
            }
        }).pipe(take(1));
    }

    uploadImage(image: AttachImage, imageType: ImageType): Observable<Image> {
        return this.http.post<Image>(ImagesMappings.UPLOAD_IMAGE_MAPPING, this.toFormData(image), {
            params: {
                imageType: imageType
            }
        }).pipe(take(1));
    }

    getCommentImage(commentId: string): Observable<void> {
        return this.http.get<void>(ImagesMappings.GET_COMMENT_IMAGE_MAPPING, {
            params: {
                commentId: commentId
            }
        }).pipe(take(1));
    }

    getPostImages(postId: string): Observable<void> {
        return this.http.get<void>(ImagesMappings.GET_POST_IMAGES_MAPPING, {
            params: {
                postId: postId
            }
        }).pipe(take(1));
    }

    getImagePath(imageId: number): Observable<string> {
        return this.http.get<string>(ImagesMappings.GET_IMAGE_MAPPING, {
            params: {
                imageId: imageId
            }
        }).pipe(take(1));
    }

    updateImage(imageUrl: string, image: AttachImage, imageType: ImageType): Observable<Image> {
        return this.http.put<Image>(ImagesMappings.UPDATE_IMAGE_MAPPING, this.toFormData(image), {
            params: {
                imageUrl: imageUrl,
                imageType: imageType
            }
        }).pipe(take(1));
    }

    getUserUploadedImages(username: string, pagingState: PageablePagingState): Observable<SocialPage<PostImage, PageablePagingState>> {
        return this.http.get<SocialPage<PostImage, PageablePagingState>>(ImagesMappings.GET_USER_UPLOADED_IMAGES_MAPPING, {
            params: {
                username: username,
                pageNumber: pagingState.pageNumber,
                pageSize: pagingState.pageSize
            }
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<PostImage, PageablePagingState>(json))
        );
    }

    deleteImage(imageId: number): Observable<void> {
        return this.http.delete<void>(ImagesMappings.DELETE_IMAGE_MAPPING, {
            params: {
                imageId: imageId
            }
        }).pipe(take(1));
    }

    deleteImageByUrl(imageUrl: string): Observable<void> {
        return this.http.delete<void>(ImagesMappings.DELETE_IMAGE_BY_URL_MAPPING, {
            params: {
                imageUrl: imageUrl
            }
        }).pipe(take(1));
    }

    deleteCommentImage(commentId: string): Observable<void> {
        return this.http.delete<void>(ImagesMappings.DELETE_COMMENT_IMAGE_BY_ID_MAPPING, {
            params: {
                commentId: commentId
            }
        }).pipe(take(1));
    }

    deletePostImageByUrl(imageUrl: string): Observable<void> {
        return this.http.delete<void>(ImagesMappings.DELETE_POST_IMAGE_BY_URL_MAPPING, {
            params: {
                imageUrl: imageUrl
            }
        }).pipe(take(1));
    }

    downloadImage(imagePath: string): Observable<any> {
        return this.http.get(imagePath, {
            responseType: 'blob'
        }).pipe(take(1));
    }

    private toFormData(image: AttachImage): FormData {
        const formData: FormData = new FormData();

        formData.append('image', image.file);

        return formData;
    }

}
