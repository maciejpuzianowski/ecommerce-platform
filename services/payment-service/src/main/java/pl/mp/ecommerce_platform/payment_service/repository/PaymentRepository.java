package pl.mp.ecommerce_platform.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mp.ecommerce_platform.payment_service.model.Payment;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
    List<Payment> findByStatus(String status);
}
