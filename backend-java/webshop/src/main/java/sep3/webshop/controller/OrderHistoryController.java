package sep3.webshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.model.OrderHistory;
import sep3.webshop.service.Impl.OrderHistoryService;

@RestController
@RequestMapping("/api/orderhistory")
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    public OrderHistoryController(OrderHistoryService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveOrder(@RequestBody OrderHistory orderHistory) {
        try {
            orderHistoryService.saveOrder(orderHistory);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}