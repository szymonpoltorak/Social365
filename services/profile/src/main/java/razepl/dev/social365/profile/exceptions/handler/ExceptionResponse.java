package razepl.dev.social365.profile.exceptions.handler;

import lombok.Builder;

@Builder
public record ExceptionResponse(String message, int statusCode, String status) {
}
