import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendsFeedComponent } from './friends-feed.component';

describe('FriendsFeedComponent', () => {
    let component: FriendsFeedComponent;
    let fixture: ComponentFixture<FriendsFeedComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [FriendsFeedComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(FriendsFeedComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
