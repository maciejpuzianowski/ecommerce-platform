package pl.mp.ecommerce_platform.order_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mp.ecommerce_platfrom.common_models.model.OrderDto;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int quantity;
    private double totalPrice;
    private String status;
    private LocalDateTime orderDate;

    public OrderDto toDto() {
        return new OrderDto(id, productId, quantity, totalPrice, status, orderDate);
    }
}