import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendFeedOptionComponent } from './friend-feed-option.component';

describe('FriendComponent', () => {
    let component: FriendFeedOptionComponent;
    let fixture: ComponentFixture<FriendFeedOptionComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [FriendFeedOptionComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(FriendFeedOptionComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
