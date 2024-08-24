package razepl.dev.social365.posts.utils.pagination.data;

import lombok.Builder;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPagingState;

@Builder
record CommentsPagingState(String pagingState, int pageSize) implements SocialPagingState {
}
