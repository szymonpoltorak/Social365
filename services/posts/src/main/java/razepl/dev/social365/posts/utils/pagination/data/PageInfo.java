package razepl.dev.social365.posts.utils.pagination.data;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import razepl.dev.social365.posts.utils.pagination.interfaces.PagingState;

import java.util.Optional;

@Slf4j
@Builder
public record PageInfo(int friendPageNumber, int pageSize, Optional<PagingState> pagingState) {

    public static PageInfo of(int pageSize, String pagingState) {
        return of(0, pageSize, pagingState);
    }

    public static PageInfo of(int pageNumber, int pageSize) {
        return of(pageNumber, pageSize, null);
    }

    public static PageInfo of(int pageNumber, int pageSize, String pagingState) {
        Optional<PagingState> cassandraPagingState = PagingState.fromString(pagingState);

        return new PageInfo(pageNumber, pageSize, cassandraPagingState);
    }

    public Pageable toPageable() {
        return pagingState
                .<Pageable>map(state -> CassandraPageRequest.of(CassandraPageRequest.first(pageSize), state.toByteBuffer()))
                .orElseGet(() -> CassandraPageRequest.first(pageSize));
    }

}
