package razepl.dev.social365.images.utils.pagination;

import org.springframework.data.domain.Slice;

import java.util.List;

public record SocialPageImpl<T> (List<T> data, PagingState pagingState, boolean hasNextPage) implements SocialPage<T>{

    public static <V> SocialPageImpl<V> from(Slice<V> page) {
        PagingState state = new PagingState(page.getNumber(), page.getSize());

        return new SocialPageImpl<>(page.getContent(), state, page.hasNext());
    }

}
