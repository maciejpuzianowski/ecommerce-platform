package pl.mp.ecommerce_platform.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mp.ecommerce_platform.product_service.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
