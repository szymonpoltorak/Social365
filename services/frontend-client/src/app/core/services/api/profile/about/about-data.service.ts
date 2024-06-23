import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, take } from "rxjs";
import { OverviewData } from "@interfaces/profile/about/overview-data.interface";
import { AboutDataMappings } from "@enums/api/profile/about/about-data-mappings.enum";
import { WorkEducation } from "@interfaces/profile/about/work-education.interface";
import { Locations } from "@interfaces/profile/about/locations.interface";
import { ContactInfo } from "@interfaces/profile/about/contact-info.interface";

@Injectable({
    providedIn: 'root'
})
export class AboutDataService {

    constructor(private http: HttpClient) {
    }

    getOverview(username: string): Observable<OverviewData> {
        return this.http.get<OverviewData>(AboutDataMappings.OVERVIEW_MAPPING, {
            params: {
                username: username
            }
        }).pipe(take(1));
    }

    getWorkEducation(username: string): Observable<WorkEducation> {
        return this.http.get<WorkEducation>(AboutDataMappings.WORK_EDUCATION_MAPPING, {
            params: {
                username: username
            }
        }).pipe(take(1));
    }

    getLocations(username: string): Observable<Locations> {
        return this.http.get<Locations>(AboutDataMappings.LOCATIONS_MAPPING, {
            params: {
                username: username
            }
        }).pipe(take(1));
    }

    getContactInfo(username: string): Observable<ContactInfo> {
        return this.http.get<ContactInfo>(AboutDataMappings.CONTACT_INFO_MAPPING, {
            params: {
                username: username
            }
        }).pipe(take(1));
    }

}
