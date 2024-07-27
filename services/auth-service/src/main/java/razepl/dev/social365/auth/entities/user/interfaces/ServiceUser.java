package razepl.dev.social365.auth.entities.user.interfaces;

import org.springframework.security.core.userdetails.UserDetails;


public interface ServiceUser extends UserDetails {

    long getUserId();

    String getFullName();

    long getJwtVersion();

    void incrementJwtVersion();

}
