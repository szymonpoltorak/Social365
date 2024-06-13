package razepl.dev.social365.posts.utils.pagination.data;

import lombok.Builder;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

import java.util.List;

@Builder
public record PostsCassandraPage<T>(List<T> data, int friendsPageNumber, int pageSize,
                                    boolean hasNextPage, String pagingState) implements CassandraPage<T> {
}
