import { Injectable } from '@angular/core';
import { Notification } from "@interfaces/notifications/notification.interface";
import { Observable, Subject } from "rxjs";
import SockJs from "sockjs-client";
import Stomp from "stompjs";
import { LocalStorageService } from "@services/utils/local-storage.service";
import { StorageKeys } from "@enums/storage-keys.enum";

@Injectable({
    providedIn: 'root'
})
export class NotificationsGatewayService {

    private notificationsSubject: Subject<Notification> = new Subject<Notification>();
    private socketClient !: Stomp.Client;
    private isConnected: boolean = false;

    constructor(private localStorage: LocalStorageService) {
    }

    connect(): Observable<Notification> {
        const socket = new SockJs(`http://localhost:8085/notifications?token=${this.localStorage.getValueFromStorage(StorageKeys.AUTH_TOKEN)}`);

        this.socketClient = Stomp.over(socket);

        this.socketClient.connect({
            // Authorization: `Bearer ${this.localStorage.getValueFromStorage(StorageKeys.AUTH_TOKEN)}`
        }, () => {
            console.log(this.socketClient.ws.url);

            this.isConnected = true;

            this.socketClient.subscribe(`/user/topic/notifications`, (message: {
                body: string;
            }) => {
                console.log(`Received message: ${ message }`);

                const notification: Notification = JSON.parse(message.body);

                this.notificationsSubject.next(notification);
            });
        });

        return this.notificationsSubject.asObservable();
    }

    disconnect(): void {
        if (!this.isConnected) {
            return;
        }
        this.socketClient.disconnect(() => this.isConnected = false);
    }

}
