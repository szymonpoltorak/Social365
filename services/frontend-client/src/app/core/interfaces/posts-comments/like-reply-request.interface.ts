import { ReplyKey } from "@interfaces/posts-comments/reply-key.interface";

export interface LikeReplyRequest {
    replyKey: ReplyKey;
    profileId: string;
}
