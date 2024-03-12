import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendProfileComponent } from './friend-profile.component';

describe('FriendProfileComponent', () => {
  let component: FriendProfileComponent;
  let fixture: ComponentFixture<FriendProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FriendProfileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FriendProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
