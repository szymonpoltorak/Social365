export enum FriendsMappings {
    FRIENDS_MAPPING = "/api/v1/profile/friends",

    FRIEND_REQUESTS = FRIENDS_MAPPING + "/friendRequests",
    
    FRIEND_SUGGESTIONS = FRIENDS_MAPPING + "/friendSuggestions",
    
    REMOVE_FRIEND = FRIENDS_MAPPING + "/removeFriend",
    
    ADD_FRIEND = FRIENDS_MAPPING + "/addFriend",
    
    GET_FRIENDS_ON_PAGE = FRIENDS_MAPPING + "/getFriends",

    GET_FOLLOWED_IDS = FRIENDS_MAPPING + "/getFollowedIds",
    
    ADD_PROFILE_TO_FOLLOWERS = FRIENDS_MAPPING + "/addProfileToFollowers",
    
    REMOVE_PROFILE_FROM_FOLLOWERS = FRIENDS_MAPPING + "/removeProfileFromFollowers",
    
    SEND_FRIEND_REQUEST = FRIENDS_MAPPING + "/sendFriendRequest",
    
    ACCEPT_FRIEND_REQUEST = FRIENDS_MAPPING + "/acceptFriendRequest",
    
    DECLINE_FRIEND_REQUEST = FRIENDS_MAPPING + "/declineFriendRequest",
}
