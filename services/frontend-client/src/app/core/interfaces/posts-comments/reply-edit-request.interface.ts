import { ReplyKey } from "@interfaces/posts-comments/reply-key.interface";

export interface ReplyEditRequest {
    replyKey: ReplyKey;
    content: string;
    hasAttachment: boolean;
}
