import { TestBed } from '@angular/core/testing';

import { RouteDetectionService } from './route-detection.service';

describe('RouteDetectionService', () => {
  let service: RouteDetectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RouteDetectionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
