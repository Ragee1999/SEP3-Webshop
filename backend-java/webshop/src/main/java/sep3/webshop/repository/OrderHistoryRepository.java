package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.OrderHistory;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {}
