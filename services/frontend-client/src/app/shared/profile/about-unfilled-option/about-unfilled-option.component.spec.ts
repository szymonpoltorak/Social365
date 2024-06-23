import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutUnfilledOptionComponent } from './about-unfilled-option.component';

describe('AboutUnfilledOptionComponent', () => {
  let component: AboutUnfilledOptionComponent;
  let fixture: ComponentFixture<AboutUnfilledOptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AboutUnfilledOptionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AboutUnfilledOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
