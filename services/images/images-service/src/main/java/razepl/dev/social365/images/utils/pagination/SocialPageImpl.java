package razepl.dev.social365.images.utils.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Slice;
import razepl.dev.social365.images.api.constants.Params;
import razepl.dev.social365.images.api.data.PostImageResponse;

import java.util.List;

public record SocialPageImpl<T> (@JsonProperty(Params.DATA) List<T> data,
                                 @JsonProperty(Params.PAGING_STATE) PagingState pagingState,
                                 @JsonProperty(Params.HAS_NEXT_PAGE) boolean hasNextPage) implements SocialPage<T>{

    public static <V> SocialPageImpl<V> from(Slice<V> page) {
        PagingState state = new PagingState(page.getNumber(), page.getSize());

        return new SocialPageImpl<>(page.getContent(), state, page.hasNext());
    }

    public static SocialPage<PostImageResponse> empty() {
        return new SocialPageImpl<>(List.of(), new PagingState(0, 0), false);
    }
}
