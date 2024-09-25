package pl.mp.ecommerce_platform.inventory_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mp.ecommerce_platform.inventory_service.model.Inventory;
import pl.mp.ecommerce_platform.inventory_service.repository.InventoryRepository;

import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory addInventory(Inventory inventory) {
        Optional<Inventory> possibleInventory = inventoryRepository.findByProductId(inventory.getProductId());
        return possibleInventory.orElseGet(() -> inventoryRepository.save(inventory));
    }

    public Optional<Inventory> getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public Inventory updateInventory(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElse(new Inventory(null, productId, 0));
        inventory.setQuantity(quantity);
        return inventoryRepository.save(inventory);
    }

    public boolean isProductInStock(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .map(inventory -> inventory.getQuantity() > 0)
                .orElse(false);
    }
}
