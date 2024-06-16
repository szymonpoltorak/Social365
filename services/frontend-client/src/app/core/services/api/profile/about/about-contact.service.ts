import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class AboutContactService {

    constructor(private http: HttpClient) {
    }

}
