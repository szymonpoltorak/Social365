import { Profile } from "@interfaces/feed/profile.interface";

export interface ProfileSummary extends Profile {
    numberOfFollowers: number;
    bio: string;
    postCount: number;
    numberOfFriends: number;
}
