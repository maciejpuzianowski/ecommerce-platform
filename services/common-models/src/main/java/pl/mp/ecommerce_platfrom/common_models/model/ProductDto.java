package pl.mp.ecommerce_platfrom.common_models.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    private String name;
    private String description;
    private double price;
}
