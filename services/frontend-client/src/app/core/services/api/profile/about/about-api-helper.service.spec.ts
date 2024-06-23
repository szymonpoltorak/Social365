import { TestBed } from '@angular/core/testing';

import { AboutApiHelperService } from './about-api-helper.service';

describe('AboutApiHelperService', () => {
  let service: AboutApiHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AboutApiHelperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
