package razepl.dev.social365.posts.api.posts.data;

import lombok.Builder;

import java.util.List;

@Builder
public record DataPage<T>(List<T> data, int pageNumber, int pageSize, boolean hasNextPage){
}
