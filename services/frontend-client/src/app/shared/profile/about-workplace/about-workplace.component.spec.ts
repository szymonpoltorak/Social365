import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutWorkplaceComponent } from './about-workplace.component';

describe('AboutWorkplaceComponent', () => {
    let component: AboutWorkplaceComponent;
    let fixture: ComponentFixture<AboutWorkplaceComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AboutWorkplaceComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(AboutWorkplaceComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
