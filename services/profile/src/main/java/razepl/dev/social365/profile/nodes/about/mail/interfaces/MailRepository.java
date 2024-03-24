package razepl.dev.social365.profile.nodes.about.mail.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.nodes.about.mail.Mail;

@Repository
public interface MailRepository extends Neo4jRepository<Mail, String> {
}
