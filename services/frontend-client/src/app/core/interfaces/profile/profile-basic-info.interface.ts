import { Profile } from "@interfaces/feed/profile.interface";

export interface ProfileBasicInfo extends Profile {
    isFriend: boolean;
    isFollowed: boolean;
}
