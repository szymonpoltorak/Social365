package razepl.dev.social365.images.exceptions.handler;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExceptionResponse(String message, int statusCode, String status, LocalDateTime timeStamp) {
}
