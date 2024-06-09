package razepl.dev.social365.posts.clients.images;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.clients.images.constants.ImagesMappings;
import razepl.dev.social365.posts.clients.images.data.CommentImage;
import razepl.dev.social365.posts.clients.images.data.PostImage;

import java.util.List;

@FeignClient(name = "IMAGES-SERVICE", url = "http://images-service:8081")
public interface ImageService {

    @GetMapping(value = ImagesMappings.GET_COMMENT_IMAGE_MAPPING)
    CommentImage getCommentImage(@RequestParam(Params.COMMENT_ID) String commentId);

    @GetMapping(value = ImagesMappings.GET_POST_IMAGES_MAPPING)
    List<PostImage> getPostImages(@RequestParam(Params.POST_ID) String postId);

}
