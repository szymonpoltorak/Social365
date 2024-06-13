package razepl.dev.social365.posts.api.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.data.PostsCassandraPage;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.post.Post;
import razepl.dev.social365.posts.entities.post.PostKey;
import razepl.dev.social365.posts.entities.post.interfaces.PostMapper;
import razepl.dev.social365.posts.entities.post.interfaces.PostRepository;
import razepl.dev.social365.posts.utils.exceptions.UserIsNotAuthorException;
import razepl.dev.social365.posts.utils.validators.interfaces.PostValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private ProfileService profileService;

    @Mock
    private PostValidator postValidator;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    final void createPost_validatesAndSavesPost() {
        String profileId = "profileId";
        String content = "content";
        Post post = Post
                .builder()
                .key(PostKey.builder().authorId(profileId).creationDateTime(LocalDateTime.now()).build())
                .content(content)
                .build();
        PostResponse postResponse = PostResponse.builder().build();

        when(postRepository.save(any(Post.class)))
                .thenReturn(post);
        when(postMapper.toPostResponseNoImages(any(Post.class), eq(profileId)))
                .thenReturn(postResponse);

        PostData result = postService.createPost(profileId, content, true);

        verify(postRepository).save(any(Post.class));
        assertEquals(postResponse, result);
    }

    @Test
    final void editPost_validatesAndUpdatesPost() {
        String profileId = "profileId";
        String postId = UUID.randomUUID().toString();
        String content = "content";
        Post post = Post
                .builder()
                .key(PostKey.builder().authorId(profileId).creationDateTime(LocalDateTime.now()).build())
                .content(content)
                .build();
        PostResponse postResponse = PostResponse.builder().build();

        when(postRepository.findById(UUID.fromString(postId)))
                .thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class)))
                .thenReturn(post);
        when(postMapper.toPostResponse(any(Post.class), eq(profileId)))
                .thenReturn(postResponse);

        PostData result = postService.editPost(profileId, postId, content);

        verify(postRepository).save(any(Post.class));
        assertEquals(postResponse, result);
    }

    @Test
    final void editPost_throwsException_whenUserIsNotAuthor() {
        String profileId = "profileId";
        String postId = UUID.randomUUID().toString();
        String content = "content";
        Post post = Post
                .builder()
                .key(PostKey.builder().authorId("differentId").creationDateTime(LocalDateTime.now()).build())
                .content(content)
                .build();

        when(postRepository.findById(UUID.fromString(postId)))
                .thenReturn(Optional.of(post));

        assertThrows(UserIsNotAuthorException.class, () -> postService.editPost(profileId, postId, content));
    }

    @Test
    final void deletePost_deletesPostAndComments() {
        String profileId = "profileId";
        String postId = UUID.randomUUID().toString();
        Post post = Post
                .builder()
                .key(PostKey.builder().authorId(profileId).postId(UUID.fromString(postId)).creationDateTime(LocalDateTime.now()).build())
                .content("content")
                .build();

        when(postRepository.findById(UUID.fromString(postId)))
                .thenReturn(Optional.of(post));

        postService.deletePost(profileId, postId);

        verify(postRepository).deleteById(UUID.fromString(postId));
    }

    @Test
    final void deletePost_throwsException_whenUserIsNotAuthor() {
        String profileId = "profileId";
        String postId = UUID.randomUUID().toString();
        Post post = Post
                .builder()
                .key(PostKey.builder().authorId("id").postId(UUID.fromString(postId)).creationDateTime(LocalDateTime.now()).build())
                .content("content")
                .build();

        when(postRepository.findById(UUID.fromString(postId)))
                .thenReturn(Optional.of(post));

        assertThrows(UserIsNotAuthorException.class, () -> postService.deletePost(profileId, postId));
    }
}
