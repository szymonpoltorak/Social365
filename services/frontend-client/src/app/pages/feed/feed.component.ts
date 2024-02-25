import { Component } from '@angular/core';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";

@Component({
    selector: 'app-feed',
    standalone: true,
    imports: [
        ToolbarComponent
    ],
    templateUrl: './feed.component.html',
    styleUrl: './feed.component.scss'
})
export class FeedComponent {

}
