package razepl.dev.social365.notifications.utils.pagination;

import java.util.List;

public interface SocialPage<T> {

    List<T> data();

    PagingState pagingState();

    boolean hasNextPage();

}
