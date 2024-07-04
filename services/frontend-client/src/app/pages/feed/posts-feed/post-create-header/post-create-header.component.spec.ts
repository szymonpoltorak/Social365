import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostCreateHeaderComponent } from './post-create-header.component';

describe('PostCreateHeaderComponent', () => {
  let component: PostCreateHeaderComponent;
  let fixture: ComponentFixture<PostCreateHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostCreateHeaderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PostCreateHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
