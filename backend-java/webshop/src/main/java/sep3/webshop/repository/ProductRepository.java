package sep3.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep3.webshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    long countByStockQuantity(int stockQuantity);
}
