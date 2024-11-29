package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {}