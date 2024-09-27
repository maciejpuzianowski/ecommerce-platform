package pl.mp.ecommerce_platform.product_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.mp.ecommerce_platfrom.common_models.model.InventoryDto;


@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @PostMapping("/api/inventory/{productId}")
    InventoryDto addInventory(@RequestBody InventoryDto inventory);
}
