import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostImageViewerComponent } from './post-image-viewer.component';

describe('PostImageViewerComponent', () => {
  let component: PostImageViewerComponent;
  let fixture: ComponentFixture<PostImageViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostImageViewerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PostImageViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
