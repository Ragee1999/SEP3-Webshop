/* package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sep3.webshop.model.CustomerOrder;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    @Query("SELECT co FROM CustomerOrder co WHERE co.customer.id = :customerId")
    List<CustomerOrder> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT co FROM CustomerOrder co WHERE co.customer.id = :customerId AND co.status = 'active'")
    Optional<CustomerOrder> findActiveOrderByCustomerId(@Param("customerId") Long customerId);


    @Query("SELECT co FROM CustomerOrder co JOIN FETCH co.customer JOIN FETCH co.address WHERE co.id = :orderId")
    Optional<CustomerOrder> findOrderWithDetails(@Param("orderId") Long orderId);

    List<CustomerOrder> findByStatus(String status);
    long countByStatus(String status);

} */