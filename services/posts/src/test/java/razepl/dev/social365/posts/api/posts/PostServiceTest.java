package razepl.dev.social365.posts.api.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import razepl.dev.social365.posts.api.posts.data.EditPostRequest;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;
import razepl.dev.social365.posts.clients.images.ImageService;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.config.auth.User;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.post.Post;
import razepl.dev.social365.posts.entities.post.PostKey;
import razepl.dev.social365.posts.entities.post.interfaces.PostMapper;
import razepl.dev.social365.posts.entities.post.interfaces.PostRepository;
import razepl.dev.social365.posts.utils.exceptions.PostDoesNotExistException;
import razepl.dev.social365.posts.utils.exceptions.UserIsNotAuthorException;
import razepl.dev.social365.posts.utils.validators.interfaces.PostValidator;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    @Mock
    private ImageService imageService;

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
                .key(PostKey.builder().authorId(profileId).creationDateTime(String.valueOf(LocalDateTime.now())).build())
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
        UUID postId = UUID.randomUUID();
        String content = "content";
        Post post = Post
                .builder()
                .key(PostKey.builder().postId(postId).authorId(profileId).creationDateTime(String.valueOf(LocalDateTime.now())).build())
                .content(content)
                .build();
        PostResponse postResponse = PostResponse.builder().build();
        EditPostRequest editPostRequest = EditPostRequest
                .builder()
                .postId(postId.toString())
                .content(content)
                .hasAttachments(false)
                .build();
        User user = User.builder().profileId(profileId).build();

        when(postRepository.findByPostId(post.getAuthorId(), post.getPostId()))
                .thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class)))
                .thenReturn(post);
        when(postMapper.toPostResponse(any(Post.class), eq(profileId)))
                .thenReturn(postResponse);

        PostData result = postService.editPost(user, editPostRequest);

        verify(postRepository).save(any(Post.class));
        assertEquals(postResponse, result);
    }

    @Test
    final void editPost_throwsException_whenUserIsNotAuthor() {
        String profileId = "profileId";
        UUID postId = UUID.randomUUID();
        String content = "content";
        Post post = Post
                .builder()
                .key(PostKey.builder().postId(postId).authorId("differentId").creationDateTime(String.valueOf(LocalDateTime.now())).build())
                .content(content)
                .build();
        EditPostRequest editPostRequest = EditPostRequest
                .builder()
                .postId(postId.toString())
                .content(content)
                .hasAttachments(false)
                .build();
        User user = User.builder().profileId(profileId).build();

        when(postRepository.findByPostId(post.getAuthorId(), post.getPostId()))
                .thenReturn(Optional.of(post));

        assertThrows(PostDoesNotExistException.class, () -> postService.editPost(user, editPostRequest));
    }

    @Test
    final void deletePost_deletesPostAndComments() {
        String profileId = "profileId";
        UUID postId = UUID.randomUUID();
        Post post = Post
                .builder()
                .key(PostKey.builder().authorId(profileId).postId(postId).creationDateTime(String.valueOf(LocalDateTime.now())).build())
                .content("content")
                .build();

        when(postRepository.findByPostId(post.getAuthorId(), post.getPostId()))
                .thenReturn(Optional.of(post));

        postService.deletePost(profileId, postId.toString(), post.getAuthorId());

        verify(postRepository).deleteByPostId(postId, post.getCreationDateTime(), profileId);
    }

    @Test
    final void deletePost_throwsException_whenUserIsNotAuthor() {
        String profileId = "profileId";
        String postId = UUID.randomUUID().toString();
        PostKey key = PostKey
                .builder()
                .authorId("id")
                .postId(UUID.fromString(postId))
                .creationDateTime(String.valueOf(LocalDateTime.now()))
                .build();
        Post post = Post
                .builder()
                .key(key)
                .content("content")
                .build();

        when(postRepository.findByPostId(key.getAuthorId(), key.getPostId()))
                .thenReturn(Optional.of(post));

        assertThrows(UserIsNotAuthorException.class, () -> postService.deletePost(profileId, postId, key.getAuthorId()));
    }
}
