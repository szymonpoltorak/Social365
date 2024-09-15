import { TestBed } from '@angular/core/testing';

import { NotificationsGatewayService } from './notifications-gateway.service';

describe('NotificationsGatewayService', () => {
  let service: NotificationsGatewayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotificationsGatewayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
