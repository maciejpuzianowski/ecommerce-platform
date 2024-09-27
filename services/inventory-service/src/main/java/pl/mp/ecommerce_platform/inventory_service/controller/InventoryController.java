package pl.mp.ecommerce_platform.inventory_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mp.ecommerce_platform.inventory_service.model.Inventory;
import pl.mp.ecommerce_platform.inventory_service.service.InventoryService;
import pl.mp.ecommerce_platfrom.common_models.model.InventoryDto;

import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryDto> getInventory(@PathVariable Long productId) {
        Optional<Inventory> opt = inventoryService.getInventoryByProductId(productId);
        return opt.map(inventory -> new ResponseEntity<>(inventory.toDto(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<InventoryDto> addInventory(@RequestBody InventoryDto inventoryDto) {
        logger.info("Adding inventory: {}", inventoryDto);
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryDto.getQuantity());
        inventory.setProductId(inventoryDto.getProductId());
        return new ResponseEntity<>(inventoryService.addInventory(inventory).toDto(), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<InventoryDto> updateInventory(@RequestParam Long productId, @RequestParam int quantity) {
        logger.info("Updating inventory for product ID: {}", productId);
        return new ResponseEntity<>(inventoryService.updateInventory(productId, quantity).toDto(), HttpStatus.OK);
    }

    @GetMapping("/{productId}/in-stock")
    public ResponseEntity<Boolean> isInStock(@PathVariable Long productId) {
        return new ResponseEntity<>(inventoryService.isProductInStock(productId), HttpStatus.OK);
    }
}
