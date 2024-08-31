package razepl.dev.social365.notifications.documents;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, UUID> {

    @Query("{ 'targetProfileId' : ?0 }")
    Slice<Notification> findAllByTargetProfileId(String targetProfileId, Pageable pageable);

    @DeleteQuery("{ 'sourceProfileId' : ?0, 'targetProfileId' : ?1 }")
    void deleteBySourceProfileIdAndTargetProfileId(String sourceProfileId, String targetProfileId);

}
