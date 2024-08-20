import { SocialPagingState } from "@core/utils/social-paging-state.interface";

export class SocialPage<T, S extends SocialPagingState<S>> implements Iterable<T> {

    private _data: T[];
    private _pagingState: S;
    private _hasNextPage: boolean;

    constructor(data: T[], pagingState: S, hasNextPage: boolean) {
        this._data = data;
        this._pagingState = pagingState;
        this._hasNextPage = hasNextPage;
    }

    get hasNextPage(): boolean {
        return this._hasNextPage;
    }

    get pagingState(): S {
        return this._pagingState;
    }

    get length(): number {
        return this._data.length;
    }

    get first(): T {
        return this._data[0];
    }

    nextPagingState(): S {
        return this._pagingState.nextPage();
    }

    [Symbol.iterator](): Iterator<T> {
        return this._data[Symbol.iterator]();
    }

    add(...element: T[]): void {
        this._data.push(...element);
    }

    static fromJson<T, S extends SocialPagingState<S>>(json: any): SocialPage<T, S> {
        return new SocialPage<T, S>(
            json.data,
            json.pagingState,
            json.hasNextPage
        );
    }

    addFirst(...element: T[]): void {
        this._data.unshift(...element);
    }

    map<V>(callback: (value: T) => V): V[] {
        return this._data.map(callback);
    }

    updatePage(page: SocialPage<T, S>): void {
        this._data = this._data.concat(page._data);
        this._pagingState = page._pagingState;
        this._hasNextPage = page._hasNextPage;
    }

    remove(element: T): void {
        this._data = this._data.filter(value => value !== element);
    }

}
