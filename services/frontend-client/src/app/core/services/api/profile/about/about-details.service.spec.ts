import { TestBed } from '@angular/core/testing';

import { AboutDetailsService } from './about-details.service';

describe('AboutDetailsService', () => {
    let service: AboutDetailsService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(AboutDetailsService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
