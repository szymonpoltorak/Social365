import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SharedPostComponent } from './shared-post.component';

describe('SharedPostComponent', () => {
    let component: SharedPostComponent;
    let fixture: ComponentFixture<SharedPostComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [SharedPostComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(SharedPostComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
