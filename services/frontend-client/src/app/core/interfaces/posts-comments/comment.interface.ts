import { Profile } from "@interfaces/feed/profile.interface";
import { Optional } from "@core/types/profile/optional.type";
import { AttachImage } from "@interfaces/feed/attach-image.interface";

export interface Comment {

    commentLikesCount: number;
    content: string;
    author: Profile;
    imageUrl: string;
    hasReplies: boolean;
    isLiked: boolean;

    updateLikesCount(): void;

    updateCommentContent(content: string, imageUrl: Optional<AttachImage>): void;

    isPostComment(): boolean;

}
