import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";

@Component({
    selector: 'app-profile',
    standalone: true,
    imports: [
        ToolbarComponent
    ],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {
    protected username: string = '';

    constructor(private activatedRoute: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.username = this.activatedRoute.snapshot.params['username'];
    }
}
