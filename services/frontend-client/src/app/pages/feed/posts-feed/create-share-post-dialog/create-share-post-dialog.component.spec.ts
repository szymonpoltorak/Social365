import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSharePostDialogComponent } from './create-share-post-dialog.component';

describe('CreateSharePostDialogComponent', () => {
  let component: CreateSharePostDialogComponent;
  let fixture: ComponentFixture<CreateSharePostDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateSharePostDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateSharePostDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
