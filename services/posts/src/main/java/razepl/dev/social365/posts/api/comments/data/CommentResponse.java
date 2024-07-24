package razepl.dev.social365.posts.api.comments.data;

import lombok.Builder;
import razepl.dev.social365.posts.clients.profile.data.Profile;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentKeyData;

@Builder
public record CommentResponse(CommentKeyData commentKey, int commentLikesCount, String content, Profile author,
                              boolean isLiked, String imageUrl, boolean hasReplies) implements Comparable<CommentResponse> {
    @Override
    public int compareTo(CommentResponse commentResponse) {
        return -commentKey.getCreationDateTime().compareTo(commentResponse.commentKey().getCreationDateTime());
    }

}
