package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {}
