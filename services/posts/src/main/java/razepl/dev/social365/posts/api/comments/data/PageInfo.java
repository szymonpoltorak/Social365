package razepl.dev.social365.posts.api.comments.data;

import lombok.Builder;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;

@Builder
public record PageInfo(int pagNumber, int pageSize) {

    public Pageable toPageable() {
        return CassandraPageRequest.of(pagNumber, pageSize);
    }

}
