package pl.mp.ecommerce_platform.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.mp.ecommerce_platform.product_service.model.Product;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/products/{productId}")
    Product getProductById(@PathVariable Long productId);
}
