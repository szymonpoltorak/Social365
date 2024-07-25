export enum RouterPaths {
    HOME = 'home',
    HOME_DIRECT = '/home',

    CURRENT_PATH = '',

    FEED = "feed",
    FEED_DIRECT = "/feed",

    UNKNOWN_PATH = "**",

    PROFILE = "profile/:username",
    PROFILE_DIRECT = "/profile",

    PROFILE_POSTS = "posts",
    FRIENDS_PATH = "friends",
    FRIENDS_DIRECT = "/friends",
    PROFILE_PHOTOS = "photos",

    PROFILE_ABOUT = "about",
    PROFILE_ABOUT_MAIN = "about/overview",
    PROFILE_ABOUT_OVERVIEW = "overview",
    PROFILE_ABOUT_WORK_EDUCATION = "work-education",
    PROFILE_ABOUT_LOCATIONS = "locations",
    PROFILE_ABOUT_CONTACT = "contact",

    FRIEND_REQUESTS = "requests",
    FRIEND_SUGGESTIONS = "suggestions",

    LOGIN_PATH = "auth/login",
    LOGIN_PATH_DIRECT = "/auth/login",

    REGISTER_PATH = "auth/register",
    REGISTER_PATH_DIRECT = "/auth/register",

    SEARCH_PATH = "search",
    SEARCH_PATH_DIRECT = "/search",

    NETWORK_OFFLINE = "offline",
    NETWORK_OFFLINE_DIRECT = "/offline",

}
