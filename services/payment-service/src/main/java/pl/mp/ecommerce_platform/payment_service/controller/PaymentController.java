package pl.mp.ecommerce_platform.payment_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mp.ecommerce_platform.payment_service.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/start")
    public ResponseEntity<String> startPayment(@RequestParam Long orderId, @RequestParam double amount) {
        logger.info("Received request to start payment for order: {}, amount: {}", orderId, amount);
        return new ResponseEntity<>(paymentService.startPayment(orderId, amount).getUrlToPayment(), HttpStatus.OK);
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<String> getStatus(@PathVariable Long paymentId) throws Exception {
        return new ResponseEntity<>(paymentService.getStatus(paymentId), HttpStatus.OK);
    }
}
