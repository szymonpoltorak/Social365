import { SocialPagingState } from "@core/utils/social-paging-state.interface";
import { Optional } from "@core/types/profile/optional.type";

export class PostsPagingState implements SocialPagingState<PostsPagingState> {

    private readonly _pageSize: number;
    private readonly _pagingState: Optional<string>;
    private _friendsPageNumber: number;

    constructor(pageSize: number, pagingState: Optional<string>, friendsPageNumber: number) {
        this._pageSize = pageSize;
        this._pagingState = pagingState;
        this._friendsPageNumber = friendsPageNumber;
    }

    nextPage(): PostsPagingState {
        this._friendsPageNumber++;

        return this;
    }

    hasPagingStarted(): boolean {
        return this._pagingState !== null;
    }

    get friendsPageNumber(): number {
        return this._friendsPageNumber;
    }

    get pageSize(): number {
        return this._pageSize;
    }

    get pagingState(): Optional<string> {
        return this._pagingState;
    }

    static firstPage(pageSize: number): PostsPagingState {
        return new PostsPagingState(pageSize, null, 0);
    }

}
