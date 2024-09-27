package pl.mp.ecommerce_platform.payment_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mp.ecommerce_platfrom.common_models.model.PaymentDto;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private double amount;
    private String status;
    private String urlToPayment;
    private LocalDateTime paymentDate;

    public PaymentDto toDto(){
        return new PaymentDto(id, orderId, amount, status, urlToPayment, paymentDate);
    }
}
