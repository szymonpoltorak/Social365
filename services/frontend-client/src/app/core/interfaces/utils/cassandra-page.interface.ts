export interface CassandraPage<T> {
    data: T[];
    friendsPageNumber: number;
    pageSize: number;
    hasNextPage: boolean;
    pagingState: string;
}
