import { TestBed } from '@angular/core/testing';

import { AboutEnumMapperService } from './about-enum-mapper.service';

describe('AboutEnumMapperService', () => {
    let service: AboutEnumMapperService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(AboutEnumMapperService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
