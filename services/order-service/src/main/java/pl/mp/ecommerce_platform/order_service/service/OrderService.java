package pl.mp.ecommerce_platform.order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mp.ecommerce_platform.order_service.client.InventoryClient;
import pl.mp.ecommerce_platform.order_service.client.PaymentClient;
import pl.mp.ecommerce_platform.order_service.client.ProductClient;
import pl.mp.ecommerce_platform.order_service.exception.OutOfStockException;
import pl.mp.ecommerce_platform.order_service.model.Order;
import pl.mp.ecommerce_platform.order_service.repository.OrderRepository;
import pl.mp.ecommerce_platfrom.common_models.model.InventoryDto;
import pl.mp.ecommerce_platfrom.common_models.model.ProductDto;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private PaymentClient paymentClient;

    public Order placeOrder(Long productId, int quantity) throws OutOfStockException {
        // Fetch product details from Product Service
        ProductDto product = productClient.getProductById(productId);

        // Check if the product is in stock from Inventory Service
        InventoryDto inventory = inventoryClient.getInventoryByProductId(productId);

        if (product == null || inventory == null) {
            throw new NoSuchElementException("Product does not exist.");
        }

        if(inventory.getQuantity() < quantity) {
            throw new OutOfStockException("Product is out of stock.");
        }

        inventoryClient.updateInventory(productId, inventory.getQuantity() - quantity);

        double totalPrice = product.getPrice() * quantity;

        Order order = new Order(null, productId, quantity, totalPrice, "PENDING", LocalDateTime.now());
        Order saved = orderRepository.save(order);
        System.out.println(paymentClient.startPayment(saved.getId(), totalPrice));
        return saved;
    }

    public String getStatus(Long orderId){
        return orderRepository.getReferenceById(orderId).getStatus();
    }

    public void updateStatus(Long orderId, String status){
        Order order = orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
        order.setStatus(status);
        orderRepository.save(order);
    }
}
