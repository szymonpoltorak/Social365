package razepl.dev.social365.auth.entities.devices.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.auth.entities.devices.LoggedDevice;
import razepl.dev.social365.auth.entities.user.User;

@Repository
public interface LoggedDeviceRepository extends JpaRepository<LoggedDevice, Long> {

    Page<LoggedDevice> findAllByUser(User user, Pageable pageable);

}
