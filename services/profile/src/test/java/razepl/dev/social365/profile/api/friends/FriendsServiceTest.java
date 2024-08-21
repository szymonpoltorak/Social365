package razepl.dev.social365.profile.api.friends;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import razepl.dev.social365.profile.api.friends.data.FriendData;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestion;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.config.User;
import razepl.dev.social365.profile.utils.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;
import razepl.dev.social365.profile.utils.pagination.SocialPage;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        ProfileRepository.class, ProfileMapper.class
})
class FriendsServiceTest {

    @InjectMocks
    private FriendsServiceImpl friendsService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Test
    final void test_getFriends_shouldReturn() {
        // given
        int page = 0;
        int size = 10;
        String profileId = "1234";
        Pageable pageable = PageRequest.of(page, size);
        User user = User.builder().profileId(profileId).build();
        FriendData friendData = FriendData
                .builder()
                .profile(Profile.builder().profileId(profileId).build())
                .isFollowed(true)
                .mutualFriendsCount(1)
                .build();
        FriendResponse friendResponse = FriendResponse
                .builder()
                .build();
        Set<FriendResponse> expected = Set.of(friendResponse);

        // when
        when(profileRepository.findFriendsByProfileId(profileId, pageable))
                .thenReturn(new PageImpl<>(List.of(friendData)));
        when(profileMapper.mapFriendDataToFriendResponse(friendData))
                .thenReturn(friendResponse);

        SocialPage<FriendResponse> actual = friendsService.getFriends(user, pageable);

        // then
        Assertions.assertEquals(expected, actual.data());
    }

    @Test
    final void test_getFriendRequests_shouldReturn() {
        // given
        int page = 0;
        int size = 10;
        String profileId = "1234";
        Pageable pageable = PageRequest.of(page, size);
        User user = User.builder().profileId(profileId).build();
        FriendSuggestion friendData = FriendSuggestion
                .builder()
                .profile(Profile.builder().profileId(profileId).build())
                .mutualFriendsCount(1)
                .build();
        FriendSuggestionResponse friendResponse = FriendSuggestionResponse
                .builder()
                .build();
        Set<FriendSuggestionResponse> expected = Set.of(friendResponse);

        // when
        when(profileRepository.findFriendRequestsByProfileId(profileId, pageable))
                .thenReturn(new PageImpl<>(List.of(friendData)));
        when(profileMapper.mapFriendSuggestionToFriendSuggestionResponse(friendData))
                .thenReturn(friendResponse);

        SocialPage<FriendSuggestionResponse> actual = friendsService.getFriendRequests(user, pageable);

        // then
        Assertions.assertEquals(expected, actual.data());
    }

    @Test
    final void test_getFollowedProfileIds_shouldReturnData() {
        // given
        String profileId = "1234";
        Profile profile = Profile.builder().profileId(profileId).build();
        List<String> expected = List.of("1234");
        User user = User.builder().profileId(profileId).build();
        Pageable pageable = PageRequest.of(0, 20);

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileRepository.findFollowedIdsByProfileId(profileId, pageable))
                .thenReturn(new PageImpl<>(expected));

        SocialPage<String> actual = friendsService.getFollowedProfileIds(user, 0);

        // then
        assertEquals(expected, actual.data(), "Should return followed profile ids");
    }

    @Test
    final void test_getFriendSuggestions_shouldReturn() {
        // given
        int page = 0;
        int size = 10;
        String profileId = "profileId";
        Pageable pageable = PageRequest.of(page, size);
        User user = User.builder().profileId(profileId).build();
        FriendSuggestion friendData = FriendSuggestion
                .builder()
                .profile(Profile.builder().profileId(profileId).build())
                .mutualFriendsCount(1)
                .build();
        FriendSuggestionResponse friendResponse = FriendSuggestionResponse
                .builder()
                .build();
        Set<FriendSuggestionResponse> expected = Set.of(friendResponse);

        // when
        when(profileRepository.findProfileSuggestions(profileId, pageable))
                .thenReturn(new PageImpl<>(List.of(friendData)));
        when(profileMapper.mapFriendSuggestionToFriendSuggestionResponse(friendData))
                .thenReturn(friendResponse);

        SocialPage<FriendSuggestionResponse> actual = friendsService.getFriendSuggestions(user, pageable);

        // then
        Assertions.assertEquals(expected, actual.data());
    }

    @Test
    final void test_removeUserFromFriends_shouldReturn() {
        // given
        String profileId = "profileId";
        String friendId = "friendId";
        User user = User.builder().profileId(profileId).build();
        Profile profile = Profile
                .builder()
                .profileId(profileId)
                .build();
        FriendResponse expected = FriendResponse.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.ofNullable(profile));
        when(profileRepository.findByProfileId(friendId))
                .thenReturn(Optional.ofNullable(Profile.builder().profileId(friendId).build()));
        when(profileRepository.areUsersFriends(profileId, friendId))
                .thenReturn(true);
        when(profileRepository.save(profile))
                .thenReturn(profile);
        when(profileMapper.mapProfileToFriendResponse(profile, -1, false))
                .thenReturn(expected);

        FriendResponse actual = friendsService.removeUserFromFriends(user, friendId);

        // then
        Assertions.assertEquals(expected, actual);
        verify(profileRepository).deleteFriendshipRelationship(profileId, friendId);
        verify(profileRepository).deleteFollowsRelation(profileId, friendId);
    }

    @Test
    final void test_addUserToFriends_shouldReturn() {
        // given
        String profileId = "profileId";
        String friendId = "friendId";
        Profile friend = Profile
                .builder()
                .profileId(friendId)
                .bio("jdhjdkjsadhkajd")
                .build();
        User user = User.builder().profileId(profileId).build();
        Profile profile = Profile
                .builder()
                .profileId(profileId)
                .build();
        FriendResponse expected = FriendResponse.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.ofNullable(profile));
        when(profileRepository.findByProfileId(friendId))
                .thenReturn(Optional.ofNullable(friend));
        when(profileRepository.save(profile))
                .thenReturn(profile);
        when(profileMapper.mapProfileToFriendResponse(profile, -1, false))
                .thenReturn(expected);

        FriendResponse actual = friendsService.addUserToFriends(user, friendId);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_addProfileToFollowed_shouldReturn() {
        // given
        String profileId = "profileId";
        String friendId = "friendId";
        Profile profile = Profile
                .builder()
                .profileId(profileId)
                .build();
        User user = User.builder().profileId(profileId).build();
        FriendResponse expected = FriendResponse.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.ofNullable(profile));
        when(profileRepository.findByProfileId(friendId))
                .thenReturn(Optional.ofNullable(Profile.builder().profileId(friendId).build()));
        when(profileRepository.save(profile))
                .thenReturn(profile);
        when(profileMapper.mapProfileToFriendResponse(profile, -1, false))
                .thenReturn(expected);

        FriendResponse actual = friendsService.addProfileToFollowed(user, friendId);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_addProfileToFollowed_throwProfileNotFoundException() {
        // given
        String profileId = "profileId";
        String friendId = "friendId";
        User user = User.builder().profileId(profileId).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> friendsService.addProfileToFollowed(user, friendId));
    }

    @Test
    final void test_addProfileToFollowed_throwProfileNotFoundExceptionOnFriend() {
        // given
        String profileId = "profileId";
        String friendId = "friendId";
        User user = User.builder().profileId(profileId).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(Profile.builder().build()));
        when(profileRepository.findByProfileId(friendId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> friendsService.addProfileToFollowed(user, friendId));
    }

}
