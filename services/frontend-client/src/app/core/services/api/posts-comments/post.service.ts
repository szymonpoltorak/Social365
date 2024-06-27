import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, take } from "rxjs";
import { Post } from "@interfaces/feed/post.interface";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { PostMappings } from "@enums/api/posts-comments/post-mappings.enum";
import { Optional } from "@core/types/profile/optional.type";
import { Either } from "@core/types/feed/either.type";
import { SharedPost } from "@interfaces/feed/shared-post.interface";

@Injectable({
    providedIn: 'root'
})
export class PostService {

    constructor(private http: HttpClient) {
    }

    getPostsOnPage(profileId: string, friendsPageNumber: number,
                   pageSize: number, pagingState: Optional<string>): Observable<CassandraPage<Either<Post, SharedPost>>> {
        return this.getPostsFromUrl(profileId, friendsPageNumber, pageSize, pagingState, PostMappings.GET_POSTS_ON_PAGE);
    }

    getUsersPosts(profileId: string, friendsPageNumber: number,
                  pageSize: number, pagingState: Optional<string>): Observable<CassandraPage<Either<Post, SharedPost>>> {
        return this.getPostsFromUrl(profileId, friendsPageNumber, pageSize, pagingState, PostMappings.GET_USERS_POSTS);
    }

    getPostsFromUrl(profileId: string, friendsPageNumber: number,
                            pageSize: number, pagingState: Optional<string>,
                            url: PostMappings): Observable<CassandraPage<Either<Post, SharedPost>>> {
        return this.http.get<CassandraPage<Either<Post, SharedPost>>>(url, {
            params: this.getHttpParams(profileId, friendsPageNumber, pageSize, pagingState)
        }).pipe(
            take(1),
            map((page: CassandraPage<Either<Post, SharedPost>>) => {
                page.data.map(post => {
                    if (post instanceof SharedPost) {
                        post.sharingPost.creationDateTime = new Date(post.sharingPost.creationDateTime);
                        post.sharedPost.creationDateTime = new Date(post.sharedPost.creationDateTime);
                    } else {
                        post.creationDateTime = new Date(post.creationDateTime);
                    }
                    return post;
                })
                return page;
            })
        );
    }

    updateLikePostCount(profileId: string, postId: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.UPDATE_LIKE_POST_COUNT, {}, {
            params: {
                profileId: profileId,
                postId: postId
            }
        }).pipe(take(1));
    }

    updateNotificationStatus(profileId: string, postId: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.UPDATE_NOTIFICATION_STATUS, {}, {
            params: {
                profileId: profileId,
                postId: postId
            }
        }).pipe(take(1));
    }

    updateBookmarkStatus(profileId: string, postId: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.UPDATE_BOOKMARK_STATUS, {}, {
            params: {
                profileId: profileId,
                postId: postId
            }
        }).pipe(take(1));
    }

    sharePost(profileId: string, postId: string): Observable<SharedPost> {
        return this.http.put<SharedPost>(PostMappings.SHARE_POST, {}, {
            params: {
                profileId: profileId,
                postId: postId
            }
        }).pipe(take(1));
    }

    createPost(profileId: string, content: string, hasAttachments: boolean): Observable<Post> {
        return this.http.post<Post>(PostMappings.CREATE_POST, {}, {
            params: {
                profileId: profileId,
                content: content,
                hasAttachments: hasAttachments
            }
        }).pipe(take(1));
    }

    editPost(profileId: string, postId: string, content: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.EDIT_POST, {}, {
            params: {
                profileId: profileId,
                postId: postId,
                content: content
            }
        }).pipe(take(1));
    }

    deletePost(profileId: string, postId: string): Observable<Post> {
        return this.http.delete<Post>(PostMappings.DELETE_POST, {
            params: {
                profileId: profileId,
                postId: postId
            }
        }).pipe(take(1));
    }

    private getHttpParams(profileId: string, friendsPageNumber: number,
                          pageSize: number, pagingState: Optional<string>): HttpParams {
        if (pagingState === null) {
            return new HttpParams()
                .set('profileId', profileId)
                .set('friendsPageNumber', friendsPageNumber)
                .set('pageSize', pageSize);
        }
        return new HttpParams()
            .set('profileId', profileId)
            .set('friendsPageNumber', friendsPageNumber)
            .set('pageSize', pageSize)
            .set('pagingState', pagingState as string);
    }

}
