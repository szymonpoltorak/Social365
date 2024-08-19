package razepl.dev.social365.posts.utils.pagination.interfaces;

import java.util.List;

public interface SocialPage<T> {

    List<T> data();

    SocialPagingState pagingState();

    boolean hasNextPage();

}
