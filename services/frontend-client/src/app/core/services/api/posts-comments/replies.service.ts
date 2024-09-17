import { Injectable } from '@angular/core';
import { map, Observable, take } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";
import { RepliesMappings } from "@enums/api/posts-comments/replies-mappings.enum";
import { ReplyAddRequest } from "@interfaces/posts-comments/reply-add-request.interface";
import { ReplyEditRequest } from "@interfaces/posts-comments/reply-edit-request.interface";
import { ReplyComment } from "@interfaces/posts-comments/reply-comment.interface";
import { ReplyKey } from "@interfaces/posts-comments/reply-key.interface";
import { SocialPage } from "@core/utils/social-page";
import { CommentsPagingState } from "@core/utils/comments-paging-state";

@Injectable({
    providedIn: 'root'
})
export class RepliesService {

    constructor(private http: HttpClient) {
    }

    getRepliesForComment(commentId: string,
                         pagingState: CommentsPagingState): Observable<SocialPage<ReplyComment, CommentsPagingState>> {
        return this.http.get<SocialPage<ReplyComment, CommentsPagingState>>(RepliesMappings.GET_REPLIES_FOR_COMMENT, {
            params: this.getCommentParams(commentId, pagingState)
        }).pipe(
            take(1),
            map(json => SocialPage.fromJson<ReplyComment, CommentsPagingState>(json, CommentsPagingState.fromJson, ReplyComment.fromJson)),
        );
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

    private getCommentParams(commentId: string, pagingState: CommentsPagingState): HttpParams {
        const params: HttpParams = new HttpParams();

        if (pagingState.hasPagingStarted()) {
            params.set('pagingState', pagingState.pagingState as string);
        }
        return params
            .set('commentId', commentId)
            .set('pageSize', pagingState.pageSize);
    }

}
