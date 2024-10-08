import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentCreateComponent } from './comment-create.component';

describe('CommentCreateComponent', () => {
    let component: CommentCreateComponent;
    let fixture: ComponentFixture<CommentCreateComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [CommentCreateComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(CommentCreateComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
