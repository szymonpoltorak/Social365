package razepl.dev.social365.images.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import razepl.dev.social365.images.api.constants.Params;

@Builder
public record CommentImageResponse(@JsonProperty(Params.IMAGE_ID) long imageId,
                                   @JsonProperty(Params.USERNAME) String username,
                                   @JsonProperty(Params.IMAGE_PATH) String imagePath,
                                   @JsonProperty(Params.COMMENT_ID) String commentId) {
}
