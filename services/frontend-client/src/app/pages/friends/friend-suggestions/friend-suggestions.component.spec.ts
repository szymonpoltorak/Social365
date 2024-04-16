import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendSuggestionsComponent } from './friend-suggestions.component';

describe('FriendSuggestionsComponent', () => {
  let component: FriendSuggestionsComponent;
  let fixture: ComponentFixture<FriendSuggestionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FriendSuggestionsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FriendSuggestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
