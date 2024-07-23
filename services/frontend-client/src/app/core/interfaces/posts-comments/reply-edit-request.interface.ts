import { ReplyKey } from "@interfaces/posts-comments/reply-key.interface";

export interface ReplyEditRequest {
    replyKey: ReplyKey;
    profileId: string;
    content: string;
    hasAttachment: boolean;
}
