import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutTypicalOptionComponent } from './about-typical-option.component';

describe('AboutTypicalOptionComponent', () => {
    let component: AboutTypicalOptionComponent;
    let fixture: ComponentFixture<AboutTypicalOptionComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AboutTypicalOptionComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(AboutTypicalOptionComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
