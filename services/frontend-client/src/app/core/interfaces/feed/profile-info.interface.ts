import { Profile } from "@interfaces/feed/profile.interface";

export interface ProfileInfo extends Profile {
    numberOfFollowers: number;
    description: string;
    postCount: number;
    numberOfFriends: number;
}
