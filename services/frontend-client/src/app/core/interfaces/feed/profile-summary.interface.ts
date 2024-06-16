import { Profile } from "@interfaces/feed/profile.interface";

export interface ProfileSummary extends Profile {
    numberOfFollowers: number;
    description: string;
    postCount: number;
    numberOfFriends: number;
}
