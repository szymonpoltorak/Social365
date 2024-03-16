import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutDateOptionComponent } from './about-date-option.component';

describe('AboutDateOptionComponent', () => {
  let component: AboutDateOptionComponent;
  let fixture: ComponentFixture<AboutDateOptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AboutDateOptionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AboutDateOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
