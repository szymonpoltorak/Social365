import { Component } from '@angular/core';
import { MatCard } from "@angular/material/card";
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatDivider } from "@angular/material/divider";
import { MatFabButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";

@Component({
    selector: 'app-posts-feed',
    standalone: true,
    imports: [
        MatCard,
        MatFormField,
        MatInput,
        MatLabel,
        MatDivider,
        MatIcon,
        MatFabButton
    ],
    templateUrl: './posts-feed.component.html',
    styleUrl: './posts-feed.component.scss'
})
export class PostsFeedComponent {

}
