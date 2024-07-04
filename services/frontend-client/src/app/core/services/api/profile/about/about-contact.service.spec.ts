import { TestBed } from '@angular/core/testing';

import { AboutContactService } from './about-contact.service';

describe('AboutContactService', () => {
    let service: AboutContactService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(AboutContactService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
