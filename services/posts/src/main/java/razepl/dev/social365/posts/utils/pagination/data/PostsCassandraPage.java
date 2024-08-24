package razepl.dev.social365.posts.utils.pagination.data;

import lombok.Builder;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import razepl.dev.social365.posts.utils.pagination.interfaces.PagingState;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPagingState;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Builder
public record PostsCassandraPage<T>(List<T> data, SocialPagingState pagingState,
                                    boolean hasNextPage) implements SocialPage<T> {

    public static <V, R> SocialPage<R> of(Pageable currentPageable, Collection<V> result,
                                         Function<V, R> mapper, int currentFriendsPage) {
        return of((CassandraPageRequest) currentPageable, result, mapper, currentFriendsPage);
    }

    public static <V, R> SocialPage<R> of(CassandraPageRequest currentPageable, Collection<V> result,
                                          Function<V, R> mapper, int currentFriendsPage) {
        CassandraPageRequest pageable = getNextPageable(currentPageable);
        PagingState pagingState = PagingState.newInstance(pageable.getPagingState());

        List<R> content = result
                .stream()
                .map(mapper)
                .sorted()
                .toList();

        PostsPagingState postsPagingState = PostsPagingState
                .builder()
                .pageSize(pageable.getPageSize())
                .pagingState(PagingState.encode(pagingState))
                .friendsPageNumber(currentFriendsPage)
                .build();

        return new PostsCassandraPage<>(content, postsPagingState, currentPageable.hasNext());
    }

    private static CassandraPageRequest getNextPageable(CassandraPageRequest pageable) {
        return pageable.hasNext() ? pageable.next() : pageable;
    }

}
