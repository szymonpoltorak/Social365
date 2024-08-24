export interface SocialPagingState<T> {

    pageSize: number;

    nextPage(): T;

}
