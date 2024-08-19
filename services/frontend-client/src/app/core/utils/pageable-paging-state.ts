import { SocialPagingState } from "@core/utils/social-paging-state.interface";

export class PageablePagingState implements SocialPagingState<PageablePagingState> {

    private readonly _pageSize: number;
    private readonly _pageNumber: number;

    constructor(pageSize: number, pageNumber: number) {
        this._pageSize = pageSize;
        this._pageNumber = pageNumber;
    }

    get pageSize(): number {
        return this._pageSize;
    }

    get pageNumber(): number {
        return this._pageNumber;
    }

    nextPage(): PageablePagingState {
        return new PageablePagingState(this._pageSize, this._pageNumber + 1);
    }

}
