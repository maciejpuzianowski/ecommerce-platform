package pl.mp.ecommerce_platform.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mp.ecommerce_platform.order_service.exception.OutOfStockException;
import pl.mp.ecommerce_platform.order_service.model.Order;
import pl.mp.ecommerce_platform.order_service.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestParam Long productId, @RequestParam int quantity) {
        try {
            return new ResponseEntity<>(orderService.placeOrder(productId, quantity), HttpStatus.CREATED);
        } catch (OutOfStockException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus(@RequestParam Long orderId) {
        return new ResponseEntity<>(orderService.getStatus(orderId), HttpStatus.OK);
    }
    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestParam Long orderId, @RequestParam String status) {
        orderService.updateStatus(orderId, status);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
