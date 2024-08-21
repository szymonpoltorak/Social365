package razepl.dev.social365.auth.api.auth.data;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ConstraintExceptionResponse(List<String> message, int statusCode, String status, LocalDateTime timeStamp) {
}
