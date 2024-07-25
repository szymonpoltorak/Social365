import { Profile } from "@interfaces/feed/profile.interface";

export interface PostKey {
    author: Profile;
    postId: string;
    creationDateTime: string;
}
