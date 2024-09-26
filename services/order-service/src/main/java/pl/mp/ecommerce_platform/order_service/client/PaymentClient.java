package pl.mp.ecommerce_platform.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service")
public interface PaymentClient {
    @PostMapping("/api/payments/start")
    String startPayment(@RequestParam Long orderId, @RequestParam double amount);
}
