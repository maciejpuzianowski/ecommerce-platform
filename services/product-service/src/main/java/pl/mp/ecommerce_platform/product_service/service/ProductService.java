package pl.mp.ecommerce_platform.product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mp.ecommerce_platform.inventory_service.model.Inventory;
import pl.mp.ecommerce_platform.product_service.client.InventoryClient;
import pl.mp.ecommerce_platform.product_service.exception.ProductNotFoundException;
import pl.mp.ecommerce_platform.product_service.model.Product;
import pl.mp.ecommerce_platform.product_service.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryClient inventoryClient;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public Product addProduct(Product product, int quantity) {
        Product saved = productRepository.save(product);
        inventoryClient.addInventory(new Inventory(null, saved.getId(), quantity));
        return productRepository.save(product);
    }

    public Product deleteProduct(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.deleteById(id);
        return product;
    }

    public Product updateProduct(Long id, Product product) throws ProductNotFoundException {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        return productRepository.save(existingProduct);
    }
}
