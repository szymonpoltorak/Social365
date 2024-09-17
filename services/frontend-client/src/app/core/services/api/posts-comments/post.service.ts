import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, take } from "rxjs";
import { Post } from "@interfaces/feed/post.interface";
import { PostMappings } from "@enums/api/posts-comments/post-mappings.enum";
import { Either } from "@core/types/feed/either.type";
import { SharedPost } from "@interfaces/feed/shared-post.interface";
import { EditPostRequest } from "@interfaces/posts-comments/edit-post-request.interface";
import { PostKey } from "@interfaces/feed/post-key.interface";
import { SocialPage } from "@core/utils/social-page";
import { PostsPagingState } from "@core/utils/posts-paging-state";

@Injectable({
    providedIn: 'root'
})
export class PostService {

    constructor(private http: HttpClient) {
    }

    getPostsFromUrl(profileId: string, pagingState: PostsPagingState,
                    url: PostMappings): Observable<SocialPage<Either<Post, SharedPost>, PostsPagingState>> {
        return this.http.get<SocialPage<Either<Post, SharedPost>, PostsPagingState>>(url, {
            params: this.getHttpParams(profileId, pagingState)
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<Either<Post, SharedPost>, PostsPagingState>(json, PostsPagingState.fromJson, this.mapPosts))
        );
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

    private mapPosts(json: any): Either<Post, SharedPost> {
        return json.sharedPost !== undefined ? SharedPost.fromJson(json) : Post.fromJson(json);
    }

    private getHttpParams(profileId: string, pagingState: PostsPagingState): HttpParams {
        const params: HttpParams = new HttpParams();

        if (pagingState.hasPagingStarted()) {
            params.set('pagingState', pagingState.pagingState as string);
        }
        return params
            .set('friendsPageNumber', pagingState.friendsPageNumber)
            .set('profileId', profileId)
            .set('pageSize', pagingState.pageSize);
    }

}
