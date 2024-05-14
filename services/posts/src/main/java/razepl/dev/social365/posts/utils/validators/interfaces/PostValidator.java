package razepl.dev.social365.posts.utils.validators.interfaces;

@FunctionalInterface
public interface PostValidator {

    void validatePostContent(String content);

}
