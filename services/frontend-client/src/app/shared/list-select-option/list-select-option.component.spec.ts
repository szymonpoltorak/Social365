import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListSelectOptionComponent } from './list-select-option.component';

describe('ListSelectOptionComponent', () => {
  let component: ListSelectOptionComponent;
  let fixture: ComponentFixture<ListSelectOptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListSelectOptionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListSelectOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
