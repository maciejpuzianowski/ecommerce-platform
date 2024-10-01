package pl.mp.ecommerce_platform.payment_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.mp.ecommerce_platform.payment_service.exception.PaymentNotFoundException;
import pl.mp.ecommerce_platform.payment_service.model.Payment;
import pl.mp.ecommerce_platform.payment_service.producer.JmsProducer;
import pl.mp.ecommerce_platform.payment_service.repository.PaymentRepository;
import pl.mp.ecommerce_platform.payment_service.service.PaymentService;

import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private JmsProducer jmsProducer;

    @BeforeEach
    public void setup() {
        // Initialize the mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStartPayment() {
        Payment payment = new Payment(null, 1L, 100.0, "IN_PROGRESS", "XXXXXXXXXXXXXXXXXX", LocalDateTime.now());
        Payment savedPayment = new Payment(1L, 1L, 100.0, "IN_PROGRESS", "XXXXXXXXXXXXXXXXXX", LocalDateTime.now());

        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        Payment result = paymentService.startPayment(1L, 100.0);

        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals("IN_PROGRESS", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    public void testCompletePayment_PaymentNotFound() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> {
            paymentService.completePayment(1L);
        });

        verify(paymentRepository, times(1)).findById(1L);
        verify(paymentRepository, never()).save(any(Payment.class));
        verify(jmsProducer, never()).sendMessage(anyString(), anyMap());
    }

    @Test
    public void testGetStatus_PaymentFound() throws PaymentNotFoundException {
        Payment payment = new Payment(1L, 1L, 100.0, "COMPLETED", "XXXXXXXXXXXXXXXXXX", LocalDateTime.now());
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        String status = paymentService.getStatus(1L);

        assertEquals("COMPLETED", status);
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetStatus_PaymentNotFound() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> {
            paymentService.getStatus(1L);
        });

        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetInProgressPayments() {
        List<Payment> payments = Arrays.asList(
                new Payment(1L, 1L, 100.0, "IN_PROGRESS", "XXXXXXXXXXXXXXXXXX", LocalDateTime.now()),
                new Payment(2L, 2L, 200.0, "IN_PROGRESS", "XXXXXXXXXXXXXXXXXX", LocalDateTime.now())
        );

        when(paymentRepository.findByStatus("IN_PROGRESS")).thenReturn(payments);

        List<Payment> result = paymentService.getInProgressPayments();

        assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findByStatus("IN_PROGRESS");
    }
}
