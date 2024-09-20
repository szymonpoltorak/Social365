import { SocialPagingState } from "@core/utils/social-paging-state.interface";
import { Optional } from "@core/types/profile/optional.type";

export class CommentsPagingState implements SocialPagingState<CommentsPagingState> {

    private readonly _pageSize: number;
    private readonly _pagingState: Optional<string>;

    constructor(pageSize: number, pagingState: Optional<string>) {
        this._pageSize = pageSize;
        this._pagingState = pagingState;
    }

    nextPage(): CommentsPagingState {
        return this;
    }

    get pageSize(): number {
        return this._pageSize;
    }

    get pagingState(): Optional<string> {
        return this._pagingState;
    }

    hasPagingStarted(): boolean {
        return this._pagingState !== null;
    }

    static first(pageSize: number): CommentsPagingState {
        return new CommentsPagingState(pageSize, null);
    }

    static fromJson(json: any): CommentsPagingState {
        return new CommentsPagingState(json.pageSize, json.pageNumber);
    }

}