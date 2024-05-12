package razepl.dev.social365.posts.entities.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.data.PostStatistics;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.clients.profile.data.Profile;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.post.interfaces.PostMapper;

@Component
@RequiredArgsConstructor
public class PostMapperImpl implements PostMapper {

    private final ProfileService profileService;
    private final CommentRepository commentRepository;

    @Override
    public final PostResponse toPostResponse(Post post, String profileId) {
        Profile author = profileService.getProfileDetails(post.getAuthorId());

        PostStatistics statistics = PostStatistics
                .builder()
                .likes(post.getUserLikedIds().size())
                .shares(post.getUserSharedIds().size())
                .comments(commentRepository.countAllByPostId(post.getPostId()))
                .build();

        return PostResponse
                .builder()
                .author(author)
                .postId(post.getPostId().toString())
                .isPostLiked(post.isPostLikedBy(profileId))
                .areNotificationTurnedOn(post.areNotificationsTurnedOnBy(profileId))
                .statistics(statistics)
                .isBookmarked(post.isBookmarkedBy(profileId))
                .creationDateTime(post.getCreationDateTime())
                .content(post.getContent())
                .build();
    }
}
