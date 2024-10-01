package pl.mp.ecommerce_platform.order_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.mp.ecommerce_platform.order_service.client.InventoryClient;
import pl.mp.ecommerce_platform.order_service.client.PaymentClient;
import pl.mp.ecommerce_platform.order_service.client.ProductClient;
import pl.mp.ecommerce_platform.order_service.exception.OutOfStockException;
import pl.mp.ecommerce_platform.order_service.model.Order;
import pl.mp.ecommerce_platform.order_service.repository.OrderRepository;
import pl.mp.ecommerce_platform.order_service.service.OrderService;
import pl.mp.ecommerce_platfrom.common_models.model.InventoryDto;
import pl.mp.ecommerce_platfrom.common_models.model.ProductDto;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductClient productClient;

    @Mock
    private InventoryClient inventoryClient;

    @Mock
    private PaymentClient paymentClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrder_Successful() throws OutOfStockException {
        // Mocking product and inventory data
        ProductDto productDto = new ProductDto(1L, "Product1", "Description1", 100.0);
        InventoryDto inventoryDto = new InventoryDto(1L, 1L, 10);

        when(productClient.getProductById(1L)).thenReturn(productDto);
        when(inventoryClient.getInventoryByProductId(1L)).thenReturn(inventoryDto);
        doNothing().when(inventoryClient).updateInventory(anyLong(), anyInt());
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L); // Simulate saved entity with generated ID
            return order;
        });
        when(paymentClient.startPayment(anyLong(), anyDouble())).thenReturn("Payment started");

        Order order = orderService.placeOrder(1L, 5);

        assertNotNull(order);
        assertEquals(1L, order.getProductId());
        assertEquals(5, order.getQuantity());
        assertEquals(500.0, order.getTotalPrice()); // Price: 100 * 5
        assertEquals("PENDING", order.getStatus());
        verify(productClient, times(1)).getProductById(1L);
        verify(inventoryClient, times(1)).getInventoryByProductId(1L);
        verify(inventoryClient, times(1)).updateInventory(1L, 5); // Updated inventory by reducing 5
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(paymentClient, times(1)).startPayment(1L, 500.0);
    }

    @Test
    public void testPlaceOrder_OutOfStock() {
        ProductDto productDto = new ProductDto(1L, "Product1", "Description1", 100.0);
        InventoryDto inventoryDto = new InventoryDto(1L, 1L, 3); // Not enough stock

        when(productClient.getProductById(1L)).thenReturn(productDto);
        when(inventoryClient.getInventoryByProductId(1L)).thenReturn(inventoryDto);

        assertThrows(OutOfStockException.class, () -> {
            orderService.placeOrder(1L, 5);
        });

        verify(productClient, times(1)).getProductById(1L);
        verify(inventoryClient, times(1)).getInventoryByProductId(1L);
        verify(inventoryClient, never()).updateInventory(anyLong(), anyInt());
        verify(orderRepository, never()).save(any(Order.class));
        verify(paymentClient, never()).startPayment(anyLong(), anyDouble());
    }

    @Test
    public void testGetStatus_OrderFound() {
        Order order = new Order(1L, 1L, 5, 500.0, "COMPLETED", LocalDateTime.now());

        when(orderRepository.getReferenceById(1L)).thenReturn(order);

        String status = orderService.getStatus(1L);

        assertEquals("COMPLETED", status);
        verify(orderRepository, times(1)).getReferenceById(1L);
    }

    @Test
    public void testUpdateStatus_Successful() {
        Order order = new Order(1L, 1L, 5, 500.0, "PENDING", LocalDateTime.now());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        orderService.updateStatus(1L, "SHIPPED");

        assertEquals("SHIPPED", order.getStatus());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testUpdateStatus_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            orderService.updateStatus(1L, "SHIPPED");
        });

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }
}