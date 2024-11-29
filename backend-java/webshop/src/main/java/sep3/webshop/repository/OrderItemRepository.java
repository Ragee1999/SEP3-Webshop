package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}