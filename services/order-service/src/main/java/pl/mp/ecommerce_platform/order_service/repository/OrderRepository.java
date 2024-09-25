package pl.mp.ecommerce_platform.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mp.ecommerce_platform.order_service.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
