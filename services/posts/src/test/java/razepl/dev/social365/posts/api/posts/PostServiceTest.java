package razepl.dev.social365.posts.api.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.post.Post;
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
    final void getPostsOnPage_fullPageReturnedByRepository() {
        String profileId = "profileId";
        int pageNumber = 1;
        int pageSize = 3;
        List<Post> posts = List.of(Post.builder().build(), Post.builder().build(), Post.builder().build());
        List<PostResponse> expected = List.of(PostResponse.builder().build(), PostResponse.builder().build(),
                PostResponse.builder().build());

        when(postRepository.findAllByFollowedUserIdsOrProfileId(any(), any()))
                .thenReturn(new SliceImpl<>(posts));
        when(profileService.getFriendsIds(eq(profileId), anyInt()))
                .thenReturn(new PageImpl<>(List.of("friendId1", "friendId2", "friendId3")));
        when(postMapper.toPostResponse(any(Post.class), eq(profileId)))
                .thenReturn(PostResponse.builder().build());

        Slice<PostData> result = postService.getPostsOnPage(profileId, pageNumber, pageSize);

        verify(postRepository).findAllByFollowedUserIdsOrProfileId(any(), any());
        assertEquals(expected, result.getContent());
    }

    @Test
    final void createPost_validatesAndSavesPost() {
        String profileId = "profileId";
        String content = "content";
        Post post = Post
                .builder()
                .authorId(profileId)
                .content(content)
                .creationDateTime(LocalDateTime.now())
                .build();
        PostResponse postResponse = PostResponse.builder().build();

        when(postRepository.save(any(Post.class)))
                .thenReturn(post);
        when(postMapper.toPostResponse(any(Post.class), eq(profileId)))
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
        Post post = Post.builder().authorId(profileId).content(content).creationDateTime(LocalDateTime.now()).build();
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
                .authorId("anotherProfileId")
                .content(content)
                .creationDateTime(LocalDateTime.now())
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
                .authorId(profileId)
                .postId(UUID.fromString(postId))
                .content("content")
                .creationDateTime(LocalDateTime.now())
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
                .authorId("anotherProfileId")
                .content("content")
                .creationDateTime(LocalDateTime.now())
                .build();

        when(postRepository.findById(UUID.fromString(postId)))
                .thenReturn(Optional.of(post));

        assertThrows(UserIsNotAuthorException.class, () -> postService.deletePost(profileId, postId));
    }
}
