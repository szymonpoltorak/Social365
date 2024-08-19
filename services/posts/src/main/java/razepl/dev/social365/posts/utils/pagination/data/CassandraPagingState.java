package razepl.dev.social365.posts.utils.pagination.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import razepl.dev.social365.posts.utils.pagination.interfaces.PagingState;

import java.nio.ByteBuffer;
import java.util.Base64;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CassandraPagingState implements PagingState {

    @JsonProperty("data")
    private String data;

    @JsonProperty("position")
    private int position;

    @JsonProperty("limit")
    private int limit;

    public CassandraPagingState(ByteBuffer buffer) {
        this.position = buffer.position();
        this.limit = buffer.limit();

        buffer.position(0);

        this.data = getDateFromBuffer(buffer);
    }

    @Override
    public final ByteBuffer toByteBuffer() {
        byte[] decodedData = Base64.getDecoder().decode(data);
        ByteBuffer buffer = ByteBuffer.wrap(decodedData);

        buffer.position(this.position);
        buffer.limit(this.limit);

        return buffer;
    }

    private static String getDateFromBuffer(ByteBuffer buffer) {
        byte[] data = new byte[buffer.remaining()];

        buffer.get(data);

        return Base64.getEncoder().encodeToString(data);
    }

}
