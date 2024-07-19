import { Injectable } from '@angular/core';
import { Optional } from "@core/types/profile/optional.type";
import { Observable } from "rxjs";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { HttpClient, HttpParams } from "@angular/common/http";
import { RepliesMappings } from "@enums/api/posts-comments/replies-mappings.enum";
import { ReplyAddRequest } from "@interfaces/posts-comments/reply-add-request.interface";
import { ReplyEditRequest } from "@interfaces/posts-comments/reply-edit-request.interface";
import { ReplyDeleteRequest } from "@interfaces/posts-comments/reply-delete-request.interface";
import { ReplyComment } from "@interfaces/posts-comments/reply-comment.interface";
import { LikeReplyRequest } from "@interfaces/posts-comments/like-reply-request.interface";

@Injectable({
    providedIn: 'root'
})
export class RepliesService {

    constructor(private http: HttpClient) {
    }

    getRepliesForComment(commentId: string, profileId: string,
                         pageSize: number, pagingState: Optional<string>): Observable<CassandraPage<ReplyComment>> {
        return this.http.get<CassandraPage<ReplyComment>>(RepliesMappings.GET_REPLIES_FOR_COMMENT, {
            params: this.getCommentParams(profileId, commentId, pageSize, pagingState)
        });
    }

    addReplyToComment(replyAddRequest: ReplyAddRequest): Observable<ReplyComment> {
        return this.http.post<ReplyComment>(RepliesMappings.ADD_REPLY_TO_COMMENT, replyAddRequest);
    }

    editReplyComment(replyEditRequest: ReplyEditRequest): Observable<ReplyComment> {
        return this.http.put<ReplyComment>(RepliesMappings.EDIT_REPLY, replyEditRequest);
    }

    deleteReplyComment(replyDeleteRequest: ReplyDeleteRequest): Observable<ReplyComment> {
        return this.http.post<ReplyComment>(RepliesMappings.DELETE_REPLY, replyDeleteRequest);
    }

    updateLikeCommentCount(likeCommentRequest: LikeReplyRequest): Observable<ReplyComment> {
        return this.http.put<ReplyComment>(RepliesMappings.UPDATE_LIKE_REPLY_COUNT, likeCommentRequest);
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

}
