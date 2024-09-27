package pl.mp.ecommerce_platform.product_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mp.ecommerce_platform.product_service.exception.ProductNotFoundException;
import pl.mp.ecommerce_platform.product_service.model.Product;
import pl.mp.ecommerce_platform.product_service.service.ProductService;
import pl.mp.ecommerce_platfrom.common_models.model.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts().stream().map(Product::toDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(productService.getProductById(id).toDto(), HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody Product product, @RequestParam int initialQuantity) {
        logger.info("Adding product: {}", product);
        return new ResponseEntity<>(productService.addProduct(product, initialQuantity).toDto(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        logger.info("Updating product with ID: {}", id);
        try {
            return new ResponseEntity<>(productService.updateProduct(id, product).toDto(), HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        try {
            Product removed = productService.deleteProduct(id);
            return new ResponseEntity<>(removed.toDto(), HttpStatus.GONE);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
