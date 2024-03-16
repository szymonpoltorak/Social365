import { AboutOptionData } from "@interfaces/profile/about/about-option-data.interface";
import { Optional } from "@core/types/profile/optional.type";
import { FormControl } from "@angular/forms";

export interface AboutOption {
    label: string;
    subLabel: string;
    data: Optional<AboutOptionData>;
    icon: string;
    nullLabel: string;
    isBeingEdited: boolean;
    formControl: FormControl<string | null | Date>;
}
