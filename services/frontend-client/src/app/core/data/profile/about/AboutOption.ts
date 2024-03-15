import { AboutOptionData } from "@core/data/profile/about/AboutOptionData";
import { Optional } from "@core/types/profile/Optional";
import { FormControl } from "@angular/forms";

export interface AboutOption {
    label: string;
    subLabel: string;
    data: Optional<AboutOptionData>;
    icon: string;
    nullLabel: string;
    isBeingEdited: boolean;
    formControl: FormControl<string | null>;
}
