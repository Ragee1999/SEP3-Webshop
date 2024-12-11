package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sep3.webshop.model.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  // Fetch order items by customer order ID
  List<OrderItem> findByCustomerOrderId(Long orderId);

  // Fetch all order items
  List<OrderItem> findAll();

  // Fetch order items with associated product details
  @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.product WHERE oi.customerOrder.id = :orderId")
  List<OrderItem> findOrderItemsWithProduct(@Param("orderId") Long orderId);
}
