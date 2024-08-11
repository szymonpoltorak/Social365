import { Profile } from "@interfaces/feed/profile.interface";
import { Token } from "@interfaces/auth/token.interface";

export interface Auth {
    profile: Profile;
    token: Token;
}
