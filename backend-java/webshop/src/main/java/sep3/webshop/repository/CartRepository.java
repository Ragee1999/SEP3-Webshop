package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
