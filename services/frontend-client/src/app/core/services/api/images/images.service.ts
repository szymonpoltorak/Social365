import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { AttachImage } from "@interfaces/feed/attach-image.interface";
import { ImagesMappings } from "@enums/api/images/images-mappings.enum";
import { Observable, take } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ImagesService {

    constructor(private http: HttpClient) {
    }

    uploadPostImage(username: string, image: AttachImage, postId: string): Observable<void> {
        return this.http.post<void>(ImagesMappings.UPLOAD_POST_IMAGE_MAPPING, this.toFormData(image), {
            params: {
                username: username,
                postId: postId
            }
        }).pipe(take(1));
    }

    uploadCommentImage(username: string, image: AttachImage, commentId: string): Observable<void> {
        return this.http.post<void>(ImagesMappings.UPLOAD_COMMENT_IMAGE_MAPPING, this.toFormData(image), {
            params: {
                username: username,
                commentId: commentId
            }
        }).pipe(take(1));
    }

    uploadImage(username: string, image: AttachImage): Observable<void> {
        return this.http.post<void>(ImagesMappings.UPLOAD_IMAGE_MAPPING, this.toFormData(image), {
            params: {
                username: username
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

    updateImage(imageId: number, image: AttachImage): Observable<void> {
        return this.http.put<void>(ImagesMappings.UPDATE_IMAGE_MAPPING, this.toFormData(image), {
            params: {
                imageId: imageId
            }
        }).pipe(take(1));
    }

    deleteImage(imageId: number): Observable<void> {
        return this.http.delete<void>(ImagesMappings.DELETE_IMAGE_MAPPING, {
            params: {
                imageId: imageId
            }
        }).pipe(take(1));
    }

    private toFormData(image: AttachImage): FormData {
        const formData: FormData = new FormData();

        formData.append('image', image.file);

        return formData;
    }

}
