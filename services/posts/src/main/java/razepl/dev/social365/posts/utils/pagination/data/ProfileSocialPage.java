package razepl.dev.social365.posts.utils.pagination.data;

import java.util.List;

public record ProfileSocialPage<T> (List<T> data, PageablePagingState pagingState, boolean hasNextPage) {

}
