package pl.mp.ecommerce_platform.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mp.ecommerce_platfrom.common_models.model.InventoryDto;

import java.util.Optional;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/api/inventory/{productId}")
    InventoryDto getInventoryByProductId (@PathVariable Long productId);

    @PutMapping("/api/inventory/update")
    void updateInventory(@RequestParam Long productId, @RequestParam int quantity);
}
