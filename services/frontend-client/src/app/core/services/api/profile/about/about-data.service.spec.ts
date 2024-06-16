import { TestBed } from '@angular/core/testing';

import { AboutDataService } from './about-data.service';

describe('AboutDataService', () => {
  let service: AboutDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AboutDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
