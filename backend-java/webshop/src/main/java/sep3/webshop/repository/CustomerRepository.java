package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}