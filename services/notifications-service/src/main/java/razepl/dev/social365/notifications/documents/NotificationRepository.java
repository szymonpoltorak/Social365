package razepl.dev.social365.notifications.documents;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, UUID> {

    Slice<Notification> findAllByTargetProfileId(String targetProfileId, Pageable pageable);

}
