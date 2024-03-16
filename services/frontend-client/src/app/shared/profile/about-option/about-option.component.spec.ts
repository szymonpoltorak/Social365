import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutOptionComponent } from './about-option.component';

describe('AboutOptionComponent', () => {
  let component: AboutOptionComponent;
  let fixture: ComponentFixture<AboutOptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AboutOptionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AboutOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
