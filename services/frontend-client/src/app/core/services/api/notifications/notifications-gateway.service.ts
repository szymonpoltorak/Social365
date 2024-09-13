import { Injectable } from '@angular/core';
import { WebSocketSubject } from "rxjs/internal/observable/dom/WebSocketSubject";
import { Notification } from "@interfaces/notifications/notification.interface";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class NotificationsGatewayService {

    private notifications$ !: WebSocketSubject<Notification>;

    connect(): Observable<Notification> {
        this.notifications$ = new WebSocketSubject<Notification>({
            url: "http://localhost:8080/notifications",
            deserializer: (e: MessageEvent) => JSON.parse(e.data)
        });

        return this.notifications$.asObservable();
    }

    disconnect(): void {
        this.notifications$.complete();
    }

}
