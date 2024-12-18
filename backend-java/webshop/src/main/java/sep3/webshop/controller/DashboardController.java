package sep3.webshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep3.webshop.repository.ContactMessageRepository;
import sep3.webshop.repository.ProductRepository;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ProductRepository productRepository;
    private final ContactMessageRepository contactMessageRepository;

    public DashboardController(
            ProductRepository productRepository,
            ContactMessageRepository contactMessageRepository
    ) {
        this.productRepository = productRepository;
        this.contactMessageRepository = contactMessageRepository;
    }


    @GetMapping("/waiting-messages")
    public ResponseEntity<Long> getWaitingMessages() {
        long count = contactMessageRepository.countByIsAnswered(false);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/sold-out-products")
    public ResponseEntity<Long> getSoldOutProducts() {
        long count = productRepository.countByStockQuantity(0);
        return ResponseEntity.ok(count);
    }
}
