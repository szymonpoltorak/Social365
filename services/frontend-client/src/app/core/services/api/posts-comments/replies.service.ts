import { Injectable } from '@angular/core';
import { Optional } from "@core/types/profile/optional.type";
import { Observable, take } from "rxjs";
import { CassandraPage } from "@interfaces/utils/cassandra-page.interface";
import { HttpClient, HttpParams } from "@angular/common/http";
import { RepliesMappings } from "@enums/api/posts-comments/replies-mappings.enum";
import { ReplyAddRequest } from "@interfaces/posts-comments/reply-add-request.interface";
import { ReplyEditRequest } from "@interfaces/posts-comments/reply-edit-request.interface";
import { ReplyComment } from "@interfaces/posts-comments/reply-comment.interface";
import { ReplyKey } from "@interfaces/posts-comments/reply-key.interface";

@Injectable({
    providedIn: 'root'
})
export class RepliesService {

    constructor(private http: HttpClient) {
    }

    getRepliesForComment(commentId: string,
                         pageSize: number, pagingState: Optional<string>): Observable<CassandraPage<ReplyComment>> {
        return this.http.get<CassandraPage<ReplyComment>>(RepliesMappings.GET_REPLIES_FOR_COMMENT, {
            params: this.getCommentParams(commentId, pageSize, pagingState)
        }).pipe(take(1));
    }

    addReplyToComment(replyAddRequest: ReplyAddRequest): Observable<ReplyComment> {
        return this.http.post<ReplyComment>(RepliesMappings.ADD_REPLY_TO_COMMENT, replyAddRequest).pipe(take(1));
    }

    editReplyComment(replyEditRequest: ReplyEditRequest): Observable<ReplyComment> {
        return this.http.put<ReplyComment>(RepliesMappings.EDIT_REPLY, replyEditRequest).pipe(take(1));
    }

    deleteReplyComment(key: ReplyKey): Observable<ReplyComment> {
        return this.http.post<ReplyComment>(RepliesMappings.DELETE_REPLY, key).pipe(take(1));
    }

    updateLikeCommentCount(key: ReplyKey): Observable<ReplyComment> {
        return this.http.put<ReplyComment>(RepliesMappings.UPDATE_LIKE_REPLY_COUNT, key).pipe(take(1));
    }

    private getCommentParams(commentId: string,
                             pageSize: number, pagingState: Optional<string>): HttpParams {
        const params: HttpParams = new HttpParams();

        if (pagingState !== null) {
            params.set('pagingState', pagingState as string);
        }
        return params
            .set('commentId', commentId)
            .set('pageSize', pageSize);
    }

}
