import { TestBed } from '@angular/core/testing';

import { AboutExperienceService } from './about-experience.service';

describe('AboutExperienceService', () => {
    let service: AboutExperienceService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(AboutExperienceService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
