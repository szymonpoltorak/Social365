import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BirthdayInfoDialogComponent } from './birthday-info-dialog.component';

describe('BirthdayInfoDialogComponent', () => {
    let component: BirthdayInfoDialogComponent;
    let fixture: ComponentFixture<BirthdayInfoDialogComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [BirthdayInfoDialogComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(BirthdayInfoDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
