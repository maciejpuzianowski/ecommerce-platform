package pl.mp.ecommerce_platform.product_service.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.mp.ecommerce_platform.inventory_service.model.Inventory;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @PostMapping("/api/inventory/{productId}")
    Inventory addInventory(@RequestBody Inventory inventory);
}
