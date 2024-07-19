import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { CommentMappings } from "@enums/api/posts-comments/comment-mappings.enum";
import { Observable } from "rxjs";
import { PostComment } from "@interfaces/posts-comments/post-comment.interface";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { CommentRequest } from "@interfaces/posts-comments/comment-request.interface";
import { Optional } from "@core/types/profile/optional.type";
import { CommentAddRequest } from "@interfaces/posts-comments/comment-add-request.interface";
import { CommentDeleteRequest } from "@interfaces/posts-comments/comment-delete-request.interface";
import { LikeCommentRequest } from "@interfaces/posts-comments/like-comment-request.interface";

@Injectable({
    providedIn: 'root'
})
export class CommentService {

    constructor(private http: HttpClient) {
    }

    getCommentsForPost(postId: string, profileId: string,
                       pageSize: number, pagingState: Optional<string>): Observable<CassandraPage<PostComment>> {
        return this.http.get<CassandraPage<PostComment>>(CommentMappings.GET_COMMENTS_FOR_POST, {
            params: this.getPostParams(profileId, postId, pageSize, pagingState)
        });
    }

    addCommentToPost(commentRequest: CommentAddRequest): Observable<PostComment> {
        return this.http.post<PostComment>(CommentMappings.ADD_COMMENT_TO_POST, commentRequest);
    }

    editComment(commentRequest: CommentRequest): Observable<PostComment> {
        return this.http.put<PostComment>(CommentMappings.EDIT_COMMENT, commentRequest);
    }

    deleteComment(commentRequest: CommentDeleteRequest): Observable<PostComment> {
        return this.http.delete<PostComment>(CommentMappings.DELETE_COMMENT, {
            body: commentRequest
        });
    }

    updateLikeCommentCount(likeCommentRequest: LikeCommentRequest): Observable<PostComment> {
        return this.http.put<PostComment>(CommentMappings.UPDATE_LIKE_COMMENT_COUNT, likeCommentRequest);
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
