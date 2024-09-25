package pl.mp.ecommerce_platform.order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mp.ecommerce_platform.inventory_service.model.Inventory;
import pl.mp.ecommerce_platform.order_service.client.InventoryClient;
import pl.mp.ecommerce_platform.order_service.client.ProductClient;
import pl.mp.ecommerce_platform.order_service.exception.OutOfStockException;
import pl.mp.ecommerce_platform.order_service.model.Order;
import pl.mp.ecommerce_platform.order_service.repository.OrderRepository;
import pl.mp.ecommerce_platform.product_service.model.Product;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private InventoryClient inventoryClient;

    public Order placeOrder(Long productId, int quantity) throws OutOfStockException {
        // Fetch product details from Product Service
        Product product = productClient.getProductById(productId);

        // Check if the product is in stock from Inventory Service
        Inventory inventory = inventoryClient.getInventoryByProductId(productId);

        if (product == null || inventory == null) {
            throw new NoSuchElementException("Product does not exist.");
        }

        if(inventory.getQuantity() < quantity) {
            throw new OutOfStockException("Product is out of stock.");
        }

        System.out.println(product);
        System.out.println(inventory);

        inventoryClient.updateInventory(productId, inventory.getQuantity() - quantity);

        double totalPrice = product.getPrice() * quantity;

        Order order = new Order(null, productId, quantity, totalPrice, "PENDING", LocalDateTime.now());
        return orderRepository.save(order);
    }

    public String getStatus(Long orderId){
        return orderRepository.getReferenceById(orderId).getStatus();
    }

    public void updateStatus(Long orderId, String status){
        Order order = orderRepository.getReferenceById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }
}
