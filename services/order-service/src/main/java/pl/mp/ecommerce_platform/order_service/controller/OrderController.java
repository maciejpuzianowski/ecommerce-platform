package pl.mp.ecommerce_platform.order_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mp.ecommerce_platform.order_service.exception.OutOfStockException;
import pl.mp.ecommerce_platform.order_service.model.Order;
import pl.mp.ecommerce_platform.order_service.service.OrderService;
import pl.mp.ecommerce_platfrom.common_models.model.OrderDto;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@RequestParam Long productId, @RequestParam int quantity) {
        logger.info("Received request to place order for product: {}, quantity: {}", productId, quantity);
        try {
            return new ResponseEntity<>(orderService.placeOrder(productId, quantity).toDto(), HttpStatus.CREATED);
        } catch (OutOfStockException e) {
            logger.error("Order failed: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus(@RequestParam Long orderId) {
        return new ResponseEntity<>(orderService.getStatus(orderId), HttpStatus.OK);
    }
    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestParam Long orderId, @RequestParam String status) {
        logger.info("Received request to update order {} status: {}",orderId , status);
        orderService.updateStatus(orderId, status);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
