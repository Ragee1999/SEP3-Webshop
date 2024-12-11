package sep3.webshop.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.model.ContactMessage;
import sep3.webshop.service.Impl.ContactMessageService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactMessageController {

    private final ContactMessageService service;

    public ContactMessageController(ContactMessageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<ContactMessage>> getAll() {
        List<ContactMessage> contactMessages = service.getAllCustomerMessages();
        return ResponseEntity.ok(contactMessages);
    }

    @PostMapping
    public ResponseEntity<String> submitContactMessage(@RequestBody ContactMessage message) {
        System.out.println("Received Contact Message: " + message);
        service.createCustomerMessage(message);
        return new ResponseEntity<>("Message received", HttpStatus.CREATED);
    }

    @PostMapping("/mark-as-answered/{id}")
    public ResponseEntity<Void> markAsAnswered(@PathVariable Long id) {
        try {
            service.markAsAnswered(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Integer>> getMessageStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("waitingMessages", service.countWaitingMessages());
        stats.put("totalMessages", service.countTotalMessages());
        stats.put("messagesThisMonth", service.countMessagesThisMonth());
        return ResponseEntity.ok(stats);
    }

    @PostMapping("/reply/{id}")
    public ResponseEntity<ContactMessage> addReply(@PathVariable Long id, @RequestBody String reply) {
        try {
            ContactMessage updatedMessage = service.addReply(id, reply);
            return ResponseEntity.ok(updatedMessage);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
