import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutSelectOptionComponent } from './about-select-option.component';

describe('AboutSelectOptionComponent', () => {
    let component: AboutSelectOptionComponent;
    let fixture: ComponentFixture<AboutSelectOptionComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AboutSelectOptionComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(AboutSelectOptionComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
