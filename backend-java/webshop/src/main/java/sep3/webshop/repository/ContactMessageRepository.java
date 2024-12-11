package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.ContactMessage;

import java.util.List;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long>
{
}
