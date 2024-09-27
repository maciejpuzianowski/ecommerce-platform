package pl.mp.ecommerce_platfrom.common_models.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;

    private Long orderId;
    private double amount;
    private String status;
    private String urlToPayment;
    private LocalDateTime paymentDate;
}
