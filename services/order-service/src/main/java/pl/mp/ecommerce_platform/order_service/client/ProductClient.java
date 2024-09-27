package pl.mp.ecommerce_platform.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.mp.ecommerce_platfrom.common_models.model.ProductDto;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/products/{productId}")
    ProductDto getProductById(@PathVariable Long productId);
}
