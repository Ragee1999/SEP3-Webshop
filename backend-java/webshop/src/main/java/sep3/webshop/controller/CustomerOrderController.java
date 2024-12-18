/* package sep3.webshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.model.CustomerOrder;
import sep3.webshop.service.CustomerOrderService;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerOrder> addToOrder(
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(customerOrderService.addToOrder(customerId, productId, quantity));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CustomerOrder>> getAllOrdersByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerOrderService.getAllOrdersByCustomer(customerId));
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getAllOrders() {
        return ResponseEntity.ok(customerOrderService.getAllOrders());
    }

    @PostMapping("/checkout/{orderId}")
    public ResponseEntity<Void> checkoutOrder(@PathVariable Long orderId) {
        customerOrderService.checkoutOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        customerOrderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> getOrderDetails(@PathVariable Long id) {
        return ResponseEntity.ok(customerOrderService.getOrderById(id));
    }

    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        customerOrderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }
} */