package sep3.webshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep3.webshop.repository.ContactMessageRepository;
import sep3.webshop.repository.CustomerOrderRepository;
import sep3.webshop.repository.ProductRepository;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductRepository productRepository;
    private final ContactMessageRepository contactMessageRepository;

    public DashboardController(
            CustomerOrderRepository customerOrderRepository,
            ProductRepository productRepository,
            ContactMessageRepository contactMessageRepository
    ) {
        this.customerOrderRepository = customerOrderRepository;
        this.productRepository = productRepository;
        this.contactMessageRepository = contactMessageRepository;
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue() {
        BigDecimal totalRevenue = customerOrderRepository
                .findByStatus("shipped")
                .stream()
                .map(order -> BigDecimal.valueOf(order.getTotalPrice())) // Convert double to BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Add BigDecimal values
        return ResponseEntity.ok(totalRevenue);
    }

    @GetMapping("/waiting-orders")
    public ResponseEntity<Long> getWaitingOrders() {
        long count = customerOrderRepository.countByStatus("waiting");
        return ResponseEntity.ok(count);
    }

    @GetMapping("/completed-orders")
    public ResponseEntity<Long> getCompletedOrders() {
        long count = customerOrderRepository.countByStatus("shipped");
        return ResponseEntity.ok(count);
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
