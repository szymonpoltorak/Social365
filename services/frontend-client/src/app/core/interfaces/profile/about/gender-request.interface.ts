import { AboutDataEmail } from "@interfaces/profile/about/about-data-email.interface";
import { GenderType } from "@enums/api/profile/about/gender-type.enum";

export interface GenderRequest extends AboutDataEmail {
    gender: GenderType;
}
