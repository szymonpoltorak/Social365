import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutWorkEducationComponent } from './about-work-education.component';

describe('AboutWorkEducationComponent', () => {
    let component: AboutWorkEducationComponent;
    let fixture: ComponentFixture<AboutWorkEducationComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AboutWorkEducationComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(AboutWorkEducationComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
