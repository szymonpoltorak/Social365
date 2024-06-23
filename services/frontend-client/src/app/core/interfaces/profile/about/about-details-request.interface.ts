import { AboutDataEmail } from "@interfaces/profile/about/about-data-email.interface";

export interface AboutDetailsRequest extends AboutDataEmail {
    detailsValue: string;
}
