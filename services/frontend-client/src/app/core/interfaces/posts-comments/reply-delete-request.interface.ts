import { ReplyKey } from "@interfaces/posts-comments/reply-key.interface";

export interface ReplyDeleteRequest {
    replyKey: ReplyKey;
    profileId: string;
}
