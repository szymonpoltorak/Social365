package razepl.dev.social365.posts.utils.pagination.data;

import lombok.Builder;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import razepl.dev.social365.posts.utils.pagination.interfaces.PagingState;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPagingState;

import java.util.List;
import java.util.function.Function;

@Builder
public record CommentsCassandraPage<T>(List<T> data, SocialPagingState pagingState,
                                       boolean hasNextPage) implements SocialPage<T> {

    public static <V, R> SocialPage<R> of(Slice<V> comments, Function<V, R> mapper) {
        List<R> data = comments
                .stream()
                .map(mapper)
                .sorted()
                .toList();

        CassandraPageRequest nextPageable = (CassandraPageRequest) getNextPageable(comments);
        PagingState pagingState = PagingState.newInstance(nextPageable.getPagingState());

        CommentsPagingState commentsPagingState = CommentsPagingState
                .builder()
                .pageSize(nextPageable.getPageSize())
                .pagingState(PagingState.encode(pagingState))
                .build();

        return new CommentsCassandraPage<>(data, commentsPagingState, nextPageable.hasNext());
    }

    private static <T> Pageable getNextPageable(Slice<T> data) {
        return data.hasNext() ? data.nextPageable() : data.getPageable();
    }

}
