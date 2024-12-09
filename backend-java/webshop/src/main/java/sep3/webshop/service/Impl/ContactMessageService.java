package sep3.webshop.service.Impl;

import org.springframework.stereotype.Service;
import sep3.webshop.model.ContactMessage;
import sep3.webshop.repository.ContactMessageRepository;

@Service
public class ContactMessageService {

    private final ContactMessageRepository repository;

    public ContactMessageService(ContactMessageRepository repository) {
        this.repository = repository;
    }

    public ContactMessage saveMessage(ContactMessage message) {
        return repository.save(message);
    }
}
