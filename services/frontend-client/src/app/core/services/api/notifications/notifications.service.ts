import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { NotificationsMappings } from "@enums/api/notifications/notifications-mappings.enum";
import { map, Observable, take } from "rxjs";
import { SocialPage } from "@core/utils/social-page";
import { PageablePagingState } from "@core/utils/pageable-paging-state";
import { Notification } from "@interfaces/notifications/notification.interface";

@Injectable({
    providedIn: 'root'
})
export class NotificationsService {

    constructor(private http: HttpClient) {
    }

    getNotificationsForUser(pagingState: PageablePagingState): Observable<SocialPage<Notification, PageablePagingState>> {
        return this.http.get<SocialPage<Notification, PageablePagingState>>(NotificationsMappings.NOTIFICATIONS_MAPPING, {
            params: {
                pageNumber: pagingState.pageNumber,
                pageSize: pagingState.pageSize
            }
        })
            .pipe(
                take(1),
                map(json => SocialPage.fromJson<Notification, PageablePagingState>(json, PageablePagingState.fromJson, (val) => val as Notification))
            );
    }

    readNotifications(): Observable<void> {
        return this.http.put<void>(NotificationsMappings.READ_NOTIFICATIONS_MAPPING, {}).pipe(take(1));
    }

}
