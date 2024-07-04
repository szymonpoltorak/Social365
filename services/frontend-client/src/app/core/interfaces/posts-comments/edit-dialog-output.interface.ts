import { AttachImage } from "@interfaces/feed/attach-image.interface";

export interface EditDialogOutput {
    newUrls: AttachImage[];
    content: string;
    deletedImages: string[];
    addedImages: AttachImage[];
}
