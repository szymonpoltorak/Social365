import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileFeedComponent } from './profile-feed.component';

describe('ProfileComponent', () => {
  let component: ProfileFeedComponent;
  let fixture: ComponentFixture<ProfileFeedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileFeedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfileFeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
