package razepl.dev.social365.images.utils.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import razepl.dev.social365.images.api.constants.Params;

record PagingState(@JsonProperty(Params.PAGE_NUMBER) int pageNumber, @JsonProperty(Params.PAGE_SIZE) int pageSize) {
}
