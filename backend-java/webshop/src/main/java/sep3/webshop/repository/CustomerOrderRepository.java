package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.CustomerOrder;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {


    // Find any orders by customer ID.

    List<CustomerOrder> findByCustomerId(Long customerId);


     // Find the cart by customer ID.


    Optional<CustomerOrder> findActiveOrderByCustomerId(Long customerId);
}
