package razepl.dev.social365.posts.utils.handlers.data;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExceptionResponse(String message, String code, String className, LocalDateTime timeStamp) {
}
