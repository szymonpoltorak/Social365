import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { CommentMappings } from "@enums/api/posts-comments/comment-mappings.enum";
import { Observable, take } from "rxjs";
import { PostComment } from "@interfaces/posts-comments/post-comment.interface";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { CommentEditRequest } from "@interfaces/posts-comments/comment-request.interface";
import { Optional } from "@core/types/profile/optional.type";
import { CommentAddRequest } from "@interfaces/posts-comments/comment-add-request.interface";
import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";

@Injectable({
    providedIn: 'root'
})
export class CommentService {

    constructor(private http: HttpClient) {
    }

    getCommentsForPost(postId: string, pageSize: number,
                       pagingState: Optional<string>): Observable<CassandraPage<PostComment>> {
        return this.http.get<CassandraPage<PostComment>>(CommentMappings.GET_COMMENTS_FOR_POST, {
            params: this.getPostParams(postId, pageSize, pagingState)
        }).pipe(take(1));
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

    private getPostParams(postId: string, pageSize: number, pagingState: Optional<string>): HttpParams {
        const params: HttpParams = new HttpParams();

        if (pagingState !== null) {
            params.set('pagingState', pagingState);
        }
        return params
            .set('pagingState', pagingState || "")
            .set('postId', postId)
            .set('pageSize', pageSize);
    }

}
