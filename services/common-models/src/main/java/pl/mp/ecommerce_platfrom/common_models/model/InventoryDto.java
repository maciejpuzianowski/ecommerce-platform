package pl.mp.ecommerce_platfrom.common_models.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {
    private Long id;

    private Long productId;
    private int quantity;
}
