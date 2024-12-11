package sep3.webshop.service.Impl;

import org.springframework.stereotype.Service;
import sep3.webshop.model.ContactMessage;
import sep3.webshop.repository.ContactMessageRepository;
import sep3.webshop.service.CustomerMessageService;

import java.util.List;

@Service
public class ContactMessageService implements CustomerMessageService
{

    private final ContactMessageRepository repository;

    public ContactMessageService(ContactMessageRepository repository) {
        this.repository = repository;
    }

    public ContactMessage saveMessage(ContactMessage message) {
        return repository.save(message);
    }

    @Override public List<ContactMessage> getAllCustomerMessages()
    {
        return repository.findAll();
    }

    @Override public ContactMessage createCustomerMessage(
        ContactMessage contactMessage)
    {
        return null;
    }
}
