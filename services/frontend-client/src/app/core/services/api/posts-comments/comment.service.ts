import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { CommentMappings } from "@enums/api/posts-comments/comment-mappings.enum";
import { map, Observable, take } from "rxjs";
import { PostComment } from "@interfaces/posts-comments/post-comment.interface";
import { CommentEditRequest } from "@interfaces/posts-comments/comment-request.interface";
import { CommentAddRequest } from "@interfaces/posts-comments/comment-add-request.interface";
import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";
import { SocialPage } from "@core/utils/social-page";
import { CommentsPagingState } from "@core/utils/comments-paging-state";

@Injectable({
    providedIn: 'root'
})
export class CommentService {

    constructor(private http: HttpClient) {
    }

    getCommentsForPost(postId: string, pagingState: CommentsPagingState): Observable<SocialPage<PostComment, CommentsPagingState>> {
        return this.http.get<SocialPage<PostComment, CommentsPagingState>>(CommentMappings.GET_COMMENTS_FOR_POST, {
            params: this.getPostParams(postId, pagingState)
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<PostComment, CommentsPagingState>(json, CommentsPagingState.fromJson))
        );
    }

    addCommentToPost(commentRequest: CommentAddRequest): Observable<PostComment> {
        return this.http.post<PostComment>(CommentMappings.ADD_COMMENT_TO_POST, commentRequest).pipe(take(1));
    }

    editComment(commentRequest: CommentEditRequest): Observable<PostComment> {
        return this.http.put<PostComment>(CommentMappings.EDIT_COMMENT, commentRequest).pipe(take(1));
    }

    deleteComment(key: CommentKey): Observable<PostComment> {
        return this.http.delete<PostComment>(CommentMappings.DELETE_COMMENT, {
            body: key
        }).pipe(take(1));
    }

    updateLikeCommentCount(key: CommentKey): Observable<PostComment> {
        return this.http.put<PostComment>(CommentMappings.UPDATE_LIKE_COMMENT_COUNT, key).pipe(take(1));
    }

    private getPostParams(postId: string, pagingState: CommentsPagingState): HttpParams {
        const params: HttpParams = new HttpParams();

        if (pagingState.hasPagingStarted()) {
            params.set('pagingState', pagingState.pagingState as string);
        }
        return params
            .set('postId', postId)
            .set('pageSize', pagingState.pageSize);
    }

}
