import { Profile } from "@interfaces/feed/profile.interface";

export interface ProfileSummary extends Profile {
    followersCount: number;
    bio: string;
    postsCount: number;
    friendsCount: number;
}
