package pl.mp.ecommerce_platform.product_service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pl.mp.ecommerce_platform.product_service.client.InventoryClient;
import pl.mp.ecommerce_platform.product_service.exception.ProductNotFoundException;
import pl.mp.ecommerce_platform.product_service.model.Product;
import pl.mp.ecommerce_platform.product_service.repository.ProductRepository;
import pl.mp.ecommerce_platform.product_service.service.ProductService;
import pl.mp.ecommerce_platfrom.common_models.model.InventoryDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryClient inventoryClient;

    @Test
    public void testGetAllProducts() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product1", "Description1", 10.0),
                new Product(2L, "Product2", "Description2", 20.0)
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById_ProductFound() throws ProductNotFoundException {
        Product product = new Product(1L, "Product1", "Description1", 10.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Product1", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductById_ProductNotFound() throws ProductNotFoundException {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        productService.getProductById(1L);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product(null, "Product1", "Description1", 10.0);
        Product savedProduct = new Product(1L, "Product1", "Description1", 10.0);
        InventoryDto inventoryDto = new InventoryDto(null, 1L, 100);

        when(productRepository.save(product)).thenReturn(savedProduct);

        // No need to mock `addInventory` with `doNothing()`, just verify it was called.
        productService.addProduct(product, 100);

        verify(productRepository, times(1)).save(product);
        verify(inventoryClient, times(1)).addInventory(any(InventoryDto.class)); // Verifies that addInventory was called
    }

    @Test
    public void testUpdateProduct() throws ProductNotFoundException {
        Product existingProduct = new Product(1L, "OldName", "OldDescription", 10.0);
        Product updatedProduct = new Product(1L, "NewName", "NewDescription", 15.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertNotNull(result);
        assertEquals("NewName", result.getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testUpdateProduct_NotFound() throws ProductNotFoundException {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product updatedProduct = new Product(1L, "NewName", "NewDescription", 15.0);

        productService.updateProduct(1L, updatedProduct);
    }

    @Test
    public void testDeleteProduct() throws ProductNotFoundException {
        Product product = new Product(1L, "Product1", "Description1", 10.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product deletedProduct = productService.deleteProduct(1L);

        assertNotNull(deletedProduct);
        assertEquals("Product1", deletedProduct.getName());
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteProduct_NotFound() throws ProductNotFoundException {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        productService.deleteProduct(1L);
    }
}
