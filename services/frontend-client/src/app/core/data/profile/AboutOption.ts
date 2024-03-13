import { AboutOptionData } from "@core/data/profile/AboutOptionData";
import { Optional } from "@core/types/profile/Optional";

export interface AboutOption {
    label: string;
    subLabel: string;
    data: Optional<AboutOptionData>;
    icon: string;
    nullLabel: string;
}
