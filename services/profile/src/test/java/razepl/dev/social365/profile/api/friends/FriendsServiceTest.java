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
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        ProfileRepository.class,
        ProfileMapper.class
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
        Pageable pageable = PageRequest.of(page, size);
        FriendData friendData = FriendData
                .builder()
                .profile(Profile.builder().profileId("profileId").build())
                .isFollowed(true)
                .mutualFriendsCount(1)
                .build();
        FriendResponse friendResponse = FriendResponse
                .builder()
                .build();
        Set<FriendResponse> expected = Set.of(friendResponse);

        // when
        when(profileRepository.findFriendsByProfileId("profileId", pageable))
                .thenReturn(new PageImpl<>(List.of(friendData)));
        when(profileMapper.mapFriendDataToFriendResponse(friendData))
                .thenReturn(friendResponse);

        Set<FriendResponse> actual = friendsService.getFriends("profileId", page, size);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_getFriendRequests_shouldReturn() {
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        FriendSuggestion friendData = FriendSuggestion
                .builder()
                .profile(Profile.builder().profileId("profileId").build())
                .mutualFriendsCount(1)
                .build();
        FriendSuggestionResponse friendResponse = FriendSuggestionResponse
                .builder()
                .build();
        Set<FriendSuggestionResponse> expected = Set.of(friendResponse);

        // when
        when(profileRepository.findFriendRequestsByProfileId("profileId", pageable))
                .thenReturn(new PageImpl<>(List.of(friendData)));
        when(profileMapper.mapFriendSuggestionToFriendSuggestionResponse(friendData))
                .thenReturn(friendResponse);

        Set<FriendSuggestionResponse> actual = friendsService.getFriendRequests("profileId", page, size);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_getFriendSuggestions_shouldReturn() {
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        FriendSuggestion friendData = FriendSuggestion
                .builder()
                .profile(Profile.builder().profileId("profileId").build())
                .mutualFriendsCount(1)
                .build();
        FriendSuggestionResponse friendResponse = FriendSuggestionResponse
                .builder()
                .build();
        Set<FriendSuggestionResponse> expected = Set.of(friendResponse);

        // when
        when(profileRepository.findProfileSuggestions("profileId", pageable))
                .thenReturn(new PageImpl<>(List.of(friendData)));
        when(profileMapper.mapFriendSuggestionToFriendSuggestionResponse(friendData))
                .thenReturn(friendResponse);

        Set<FriendSuggestionResponse> actual = friendsService.getFriendSuggestions("profileId", page, size);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_removeUserFromFriends_shouldReturn() {
        // given
        String profileId = "profileId";
        String friendId = "friendId";
        Profile friend = Profile.builder().build();

        Set<Profile> set = new HashSet<>();
        set.add(friend);

        Profile profile = Profile
                .builder()
                .friends(set)
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

        FriendResponse actual = friendsService.removeUserFromFriends(profileId, friendId);

        // then
        Assertions.assertEquals(expected, actual);
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
                .friends(new HashSet<>())
                .followers(new HashSet<>())
                .build();

        Profile profile = Profile
                .builder()
                .profileId(profileId)
                .friends(new HashSet<>())
                .followers(new HashSet<>())
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

        FriendResponse actual = friendsService.addUserToFriends(profileId, friendId);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_changeFollowStatus_shouldReturn() {
        // given
        String profileId = "profileId";
        String friendId = "friendId";
        Profile friend = Profile.builder().build();

        Set<Profile> set = new HashSet<>();
        set.add(friend);

        Profile profile = Profile
                .builder()
                .followers(set)
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

        FriendResponse actual = friendsService.changeFollowStatus(profileId, friendId);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_changeFollowStatus_throwProfileNotFoundException() {
        // given
        String profileId = "profileId";
        String friendId = "friendId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> friendsService.changeFollowStatus(profileId, friendId));
    }

    @Test
    final void test_changeFollowStatus_throwProfileNotFoundExceptionOnFriend() {
        // given
        String profileId = "profileId";
        String friendId = "friendId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(Profile.builder().build()));
        when(profileRepository.findByProfileId(friendId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> friendsService.changeFollowStatus(profileId, friendId));
    }

}
