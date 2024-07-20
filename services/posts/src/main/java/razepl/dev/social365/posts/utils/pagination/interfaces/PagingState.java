package razepl.dev.social365.posts.utils.pagination.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import razepl.dev.social365.posts.utils.pagination.data.CassandraPagingState;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;


@FunctionalInterface
public interface PagingState {

    ByteBuffer toByteBuffer();

    static String encode(PagingState pagingState) {
        try {
            if (pagingState == null) {
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(pagingState);

            return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.ISO_8859_1));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while encoding paging state", e);
        }
    }

    static Optional<PagingState> fromString(String base64String) {
        try {
            if (base64String == null || base64String.isEmpty()) {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String pagingStateJson = new String(Base64.getDecoder().decode(base64String), StandardCharsets.ISO_8859_1);

            return Optional.of(objectMapper.readValue(pagingStateJson, CassandraPagingState.class));

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while parsing paging state", e);
        }
    }

    static PagingState newInstance(ByteBuffer buffer) {
        if (buffer == null) {
            return null;
        }
        return new CassandraPagingState(buffer);
    }

}
