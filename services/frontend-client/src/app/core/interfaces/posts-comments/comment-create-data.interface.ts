import { Optional } from "@core/types/profile/optional.type";
import { AttachImage } from "@interfaces/feed/attach-image.interface";

export interface CommentCreateData {
    content: string;
    attachedImage: Optional<AttachImage>;
}
