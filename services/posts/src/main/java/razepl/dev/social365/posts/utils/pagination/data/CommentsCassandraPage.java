package razepl.dev.social365.posts.utils.pagination.data;

import lombok.Builder;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;
import razepl.dev.social365.posts.utils.pagination.interfaces.PagingState;

import java.util.List;
import java.util.function.Function;

@Builder
public record CommentsCassandraPage<T>(List<T> data, int pageSize, boolean hasNextPage,
                                       PagingState pagingState) implements CassandraPage<T> {

    public static <V, R> CassandraPage<R> of(Slice<V> comments, Function<V, R> mapper) {
        List<R> data = comments.stream().map(mapper).toList();

        CassandraPageRequest nextPageable = (CassandraPageRequest) comments.nextPageable();
        PagingState pagingState = PagingState.newInstance(nextPageable.getPagingState());

        return new CommentsCassandraPage<>(data, nextPageable.getPageSize(), nextPageable.hasNext(), pagingState);
    }

}
