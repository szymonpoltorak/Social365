import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileAboutComponent } from './profile-about.component';

describe('ProfileAboutComponent', () => {
    let component: ProfileAboutComponent;
    let fixture: ComponentFixture<ProfileAboutComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [ProfileAboutComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(ProfileAboutComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
