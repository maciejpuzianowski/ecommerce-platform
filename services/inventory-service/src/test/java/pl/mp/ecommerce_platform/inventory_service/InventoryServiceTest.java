package pl.mp.ecommerce_platform.inventory_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.mp.ecommerce_platform.inventory_service.model.Inventory;
import pl.mp.ecommerce_platform.inventory_service.repository.InventoryRepository;
import pl.mp.ecommerce_platform.inventory_service.service.InventoryService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddInventory_NewInventory() {
        Inventory inventory = new Inventory(null, 1L, 100);

        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        Inventory result = inventoryService.addInventory(inventory);

        assertNotNull(result);
        assertEquals(1L, result.getProductId());
        assertEquals(100, result.getQuantity());
        verify(inventoryRepository, times(1)).findByProductId(1L);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    public void testAddInventory_ExistingInventory() {
        Inventory existingInventory = new Inventory(1L, 1L, 50);
        Inventory newInventory = new Inventory(1L, 1L, 100);

        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(existingInventory));

        Inventory result = inventoryService.addInventory(newInventory);

        assertNotNull(result);
        assertEquals(50, result.getQuantity()); // Existing inventory, so quantity stays 50
        verify(inventoryRepository, times(1)).findByProductId(1L);
        verify(inventoryRepository, never()).save(any(Inventory.class)); // Save should not be called
    }

    @Test
    public void testGetInventoryByProductId_InventoryFound() {
        Inventory inventory = new Inventory(1L, 1L, 100);

        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));

        Optional<Inventory> result = inventoryService.getInventoryByProductId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getProductId());
        assertEquals(100, result.get().getQuantity());
        verify(inventoryRepository, times(1)).findByProductId(1L);
    }

    @Test
    public void testGetInventoryByProductId_InventoryNotFound() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());

        Optional<Inventory> result = inventoryService.getInventoryByProductId(1L);

        assertFalse(result.isPresent());
        verify(inventoryRepository, times(1)).findByProductId(1L);
    }

    @Test
    public void testUpdateInventory_ExistingInventory() {
        Inventory existingInventory = new Inventory(1L, 1L, 50);

        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(existingInventory));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(existingInventory);

        Inventory result = inventoryService.updateInventory(1L, 150);

        assertNotNull(result);
        assertEquals(150, result.getQuantity()); // Quantity updated to 150
        verify(inventoryRepository, times(1)).findByProductId(1L);
        verify(inventoryRepository, times(1)).save(existingInventory);
    }

    @Test
    public void testUpdateInventory_NewInventory() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());
        Inventory newInventory = new Inventory(null, 1L, 150);

        when(inventoryRepository.save(any(Inventory.class))).thenReturn(newInventory);

        Inventory result = inventoryService.updateInventory(1L, 150);

        assertNotNull(result);
        assertEquals(1L, result.getProductId());
        assertEquals(150, result.getQuantity()); // New inventory created with quantity 150
        verify(inventoryRepository, times(1)).findByProductId(1L);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    public void testIsProductInStock_ProductInStock() {
        Inventory inventory = new Inventory(1L, 1L, 100);

        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));

        boolean result = inventoryService.isProductInStock(1L);

        assertTrue(result);
        verify(inventoryRepository, times(1)).findByProductId(1L);
    }

    @Test
    public void testIsProductInStock_ProductOutOfStock() {
        Inventory inventory = new Inventory(1L, 1L, 0);

        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));

        boolean result = inventoryService.isProductInStock(1L);

        assertFalse(result);
        verify(inventoryRepository, times(1)).findByProductId(1L);
    }

    @Test
    public void testIsProductInStock_ProductNotFound() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());

        boolean result = inventoryService.isProductInStock(1L);

        assertFalse(result);
        verify(inventoryRepository, times(1)).findByProductId(1L);
    }
}

