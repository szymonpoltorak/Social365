import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { Post } from "@interfaces/feed/post.interface";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { PostMappings } from "@enums/api/posts-comments/post-mappings.enum";

@Injectable({
    providedIn: 'root'
})
export class PostService {

    constructor(private http: HttpClient) {
    }

    getPostsOnPage(profileId: string, friendsPageNumber: number,
                   pageSize: number, pagingState: string): Observable<CassandraPage<Post>> {
        return this.http.get<CassandraPage<Post>>(PostMappings.GET_POSTS_ON_PAGE, {
            params: {
                profileId: profileId,
                friendsPageNumber: friendsPageNumber,
                pageSize: pageSize,
                pagingState: pagingState
            }
        });
    }

    updateLikePostCount(profileId: string, postId: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.UPDATE_LIKE_POST_COUNT, {}, {
            params: {
                profileId: profileId,
                postId: postId
            }
        });
    }

    updateNotificationStatus(profileId: string, postId: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.UPDATE_NOTIFICATION_STATUS, {}, {
            params: {
                profileId: profileId,
                postId: postId
            }
        });
    }

    updateBookmarkStatus(profileId: string, postId: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.UPDATE_BOOKMARK_STATUS, {}, {
            params: {
                profileId: profileId,
                postId: postId
            }
        });
    }

    sharePost(profileId: string, postId: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.SHARE_POST, {}, {
            params: {
                profileId: profileId,
                postId: postId
            }
        });
    }

    createPost(profileId: string, content: string, hasAttachments: boolean): Observable<Post> {
        return this.http.post<Post>(PostMappings.CREATE_POST, {}, {
            params: {
                profileId: profileId,
                content: content,
                hasAttachments: hasAttachments
            }
        });
    }

    editPost(profileId: string, postId: string, content: string): Observable<Post> {
        return this.http.put<Post>(PostMappings.EDIT_POST, {}, {
            params: {
                profileId: profileId,
                postId: postId,
                content: content
            }
        });
    }

    deletePost(profileId: string, postId: string): Observable<Post> {
        return this.http.delete<Post>(PostMappings.DELETE_POST, {
            params: {
                profileId: profileId,
                postId: postId
            }
        });
    }

}
