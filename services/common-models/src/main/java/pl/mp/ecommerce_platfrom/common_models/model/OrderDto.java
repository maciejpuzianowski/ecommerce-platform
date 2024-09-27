package pl.mp.ecommerce_platfrom.common_models.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;

    private Long productId;
    private int quantity;
    private double totalPrice;
    private String status;
    private LocalDateTime orderDate;
}
