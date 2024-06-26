import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { CommentMappings } from "@enums/api/posts-comments/comment-mappings.enum";
import { Observable } from "rxjs";
import { PostComment } from "@interfaces/feed/post-comment.interface";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { CommentRequest } from "@interfaces/posts-comments/comment-request.interface";

@Injectable({
    providedIn: 'root'
})
export class CommentService {

    constructor(private http: HttpClient) {
    }

    getRepliesForComment(commentId: string, profileId: string,
                         pageSize: number, pagingState: string): Observable<CassandraPage<PostComment>> {
        return this.http.get<CassandraPage<PostComment>>(CommentMappings.GET_REPLIES_FOR_COMMENT, {
            params: {
                commentId: commentId,
                profileId: profileId,
                pageSize: pageSize,
                pagingState: pagingState
            }
        });
    }

    getCommentsForPost(postId: string, profileId: string,
                       pageSize: number, pagingState: string): Observable<CassandraPage<PostComment>> {
        return this.http.get<CassandraPage<PostComment>>(CommentMappings.GET_COMMENTS_FOR_POST, {
            params: {
                postId: postId,
                profileId: profileId,
                pageSize: pageSize,
                pagingState: pagingState
            }
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

}
