package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long>
{
    long countByIsAnswered(boolean isAnswered);
}
