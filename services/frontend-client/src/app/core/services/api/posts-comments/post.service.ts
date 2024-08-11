import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, take } from "rxjs";
import { Post } from "@interfaces/feed/post.interface";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { PostMappings } from "@enums/api/posts-comments/post-mappings.enum";
import { Optional } from "@core/types/profile/optional.type";
import { Either } from "@core/types/feed/either.type";
import { SharedPost } from "@interfaces/feed/shared-post.interface";
import { EditPostRequest } from "@interfaces/posts-comments/edit-post-request.interface";
import { PostKey } from "@interfaces/feed/post-key.interface";

@Injectable({
    providedIn: 'root'
})
export class PostService {

    constructor(private http: HttpClient) {
    }

    getPostsFromUrl(profileId: string, friendsPageNumber: number,
                    pageSize: number, pagingState: Optional<string>,
                    url: PostMappings): Observable<CassandraPage<Either<Post, SharedPost>>> {
        return this.http.get<CassandraPage<Either<Post, SharedPost>>>(url, {
            params: this.getHttpParams(profileId, friendsPageNumber, pageSize, pagingState)
        }).pipe(take(1));
    }

    updateLikePostCount(postId: string, authorId: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.UPDATE_LIKE_POST_COUNT, {}, {
            params: {
                postId: postId,
                authorId: authorId
            }
        }).pipe(take(1));
    }

    updateNotificationStatus(postId: string, authorId: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.UPDATE_NOTIFICATION_STATUS, {}, {
            params: {
                postId: postId,
                authorId: authorId
            }
        }).pipe(take(1));
    }

    updateBookmarkStatus(postId: string, authorId: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.UPDATE_BOOKMARK_STATUS, {}, {
            params: {
                postId: postId,
                authorId: authorId
            }
        }).pipe(take(1));
    }

    sharePost(postKey: PostKey, content: string): Observable<SharedPost> {
        return this.http.put<SharedPost>(PostMappings.SHARE_POST, {}, {
            params: {
                postId: postKey.postId,
                authorId: postKey.author.profileId,
                content: content
            }
        }).pipe(take(1));
    }

    createPost(content: string, hasAttachments: boolean): Observable<Post> {
        return this.http.post<Post>(PostMappings.CREATE_POST, {}, {
            params: {
                content: content,
                hasAttachments: hasAttachments
            }
        }).pipe(take(1));
    }

    editPost(request: EditPostRequest): Observable<Post> {
        return this.http.put<Post>(PostMappings.EDIT_POST, request).pipe(take(1));
    }

    deletePost(postId: string, authorId: string): Observable<Post> {
        return this.http.delete<Post>(PostMappings.DELETE_POST, {
            params: {
                postId: postId,
                authorId: authorId
            }
        }).pipe(take(1));
    }

    private getHttpParams(profileId: string, friendsPageNumber: number, pageSize: number,
                          pagingState: Optional<string>): HttpParams {
        const params: HttpParams = new HttpParams();

        if (pagingState !== null) {
            params.set('pagingState', pagingState || "");
        }
        return params
            .set('friendsPageNumber', friendsPageNumber)
            .set('profileId', profileId)
            .set('pageSize', pageSize);
    }

}
