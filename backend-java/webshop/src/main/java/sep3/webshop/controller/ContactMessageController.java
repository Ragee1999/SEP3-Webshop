package sep3.webshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.model.ContactMessage;
import sep3.webshop.repository.ContactMessageRepository;
import sep3.webshop.service.Impl.ContactMessageService;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactMessageController {

    private final ContactMessageService service;

    public ContactMessageController(ContactMessageService contactMessageService) {
        this.service = contactMessageService;
    }

    @GetMapping public ResponseEntity<Iterable<ContactMessage>> getAll()
    {
        List<ContactMessage> contactMessages = service.getAllCustomerMessages();
        return ResponseEntity.ok(contactMessages);
    }

    @PostMapping public ResponseEntity<String> submitContactMessage(@RequestBody ContactMessage message)
    {
        System.out.println("Received Contact Message: " + message);
        service.createCustomerMessage(message);
        return new ResponseEntity<>("Message received", HttpStatus.CREATED);
    }

}
