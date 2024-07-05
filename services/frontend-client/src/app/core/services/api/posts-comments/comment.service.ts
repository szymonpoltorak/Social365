import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { CommentMappings } from "@enums/api/posts-comments/comment-mappings.enum";
import { Observable } from "rxjs";
import { PostComment } from "@interfaces/feed/post-comment.interface";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { CommentRequest } from "@interfaces/posts-comments/comment-request.interface";
import { Optional } from "@core/types/profile/optional.type";

@Injectable({
    providedIn: 'root'
})
export class CommentService {

    constructor(private http: HttpClient) {
    }

    getRepliesForComment(commentId: string, profileId: string,
                         pageSize: number, pagingState: Optional<string>): Observable<CassandraPage<PostComment>> {
        return this.http.get<CassandraPage<PostComment>>(CommentMappings.GET_REPLIES_FOR_COMMENT, {
            params: this.getCommentParams(profileId, commentId, pageSize, pagingState)
        });
    }

    getCommentsForPost(postId: string, profileId: string,
                       pageSize: number, pagingState: Optional<string>): Observable<CassandraPage<PostComment>> {
        return this.http.get<CassandraPage<PostComment>>(CommentMappings.GET_COMMENTS_FOR_POST, {
            params: this.getPostParams(profileId, postId, pageSize, pagingState)
        });
    }

    addCommentToPost(commentRequest: CommentRequest): Observable<PostComment> {
        return this.http.post<PostComment>(CommentMappings.ADD_COMMENT_TO_POST, commentRequest);
    }

    editComment(commentRequest: CommentRequest): Observable<PostComment> {
        return this.http.put<PostComment>(CommentMappings.EDIT_COMMENT, commentRequest);
    }

    deleteComment(commentId: string, profileId: string): Observable<PostComment> {
        return this.http.delete<PostComment>(CommentMappings.DELETE_COMMENT, {
            params: {
                commentId: commentId,
                profileId: profileId
            }
        });
    }

    private getCommentParams(profileId: string, commentId: string,
                             pageSize: number, pagingState: Optional<string>): HttpParams {
        const params: HttpParams = new HttpParams();

        if (pagingState !== null) {
            params.set('pagingState', pagingState as string);
        }
        return params
            .set('profileId', profileId)
            .set('commentId', commentId)
            .set('pageSize', pageSize);
    }

    private getPostParams(profileId: string, postId: string,
                          pageSize: number, pagingState: Optional<string>): HttpParams {
        const params: HttpParams = new HttpParams();

        if (pagingState !== null) {
            params.set('pagingState', pagingState as string);
        }
        return params
            .set('profileId', profileId)
            .set('postId', postId)
            .set('pageSize', pageSize);
    }

}
