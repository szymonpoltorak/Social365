import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutRelationshipComponent } from './about-relationship.component';

describe('AboutRelationshipComponent', () => {
    let component: AboutRelationshipComponent;
    let fixture: ComponentFixture<AboutRelationshipComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AboutRelationshipComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(AboutRelationshipComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
