package sep3.webshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.model.ContactMessage;
import sep3.webshop.repository.ContactMessageRepository;

@RestController
@RequestMapping("/api/contact")
public class ContactMessageController {

    private final ContactMessageRepository repository;

    public ContactMessageController(ContactMessageRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<String> submitContactMessage(@RequestBody ContactMessage message) {
        System.out.println("Received Contact Message: " + message);
        repository.save(message);
        return new ResponseEntity<>("Message received", HttpStatus.CREATED);
    }

}
