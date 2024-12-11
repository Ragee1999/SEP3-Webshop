package sep3.webshop.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sep3.webshop.model.ContactMessage;
import sep3.webshop.repository.ContactMessageRepository;
import sep3.webshop.service.CustomerMessageService;

import java.time.LocalDate;
import java.time.ZoneId;
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

    @Override
    public ContactMessage createCustomerMessage(ContactMessage contactMessage) {
        return repository.save(contactMessage);
    }

    public void markAsAnswered(Long id) {
        ContactMessage message = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + id));
        message.setIsAnswered(true);
        repository.save(message);
    }

    public int countWaitingMessages() {
        return (int) repository.findAll().stream().filter(message -> !message.isAnswered()).count();
    }

    public int countTotalMessages() {
        return (int) repository.count();
    }

    public int countMessagesThisMonth() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        return (int) repository.findAll().stream()
                .filter(message -> message.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(startOfMonth))
                .count();
    }

    public ContactMessage addReply(Long id, String reply) {
        ContactMessage message = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + id));
        message.setReply(reply);
        message.setIsAnswered(true);
        return repository.save(message);
    }
}
