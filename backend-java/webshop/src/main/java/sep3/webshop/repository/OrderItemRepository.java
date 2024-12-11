package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


  // Find all order items by customer order ID.
    List<OrderItem> findByCustomerOrderId(Long orderId);

    List<OrderItem> findAll();
}
