package razepl.dev.social365.posts.utils.pagination.data;

import lombok.Builder;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;
import razepl.dev.social365.posts.utils.pagination.interfaces.PagingState;

import java.util.List;

@Builder
public record CommentsCassandraPage<T>(List<T> data, int pageSize, boolean hasNextPage,
                                       PagingState pagingState) implements CassandraPage<T> {
}
