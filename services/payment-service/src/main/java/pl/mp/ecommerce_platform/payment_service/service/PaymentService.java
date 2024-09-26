package pl.mp.ecommerce_platform.payment_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.mp.ecommerce_platform.payment_service.exception.PaymentNotFoundException;
import pl.mp.ecommerce_platform.payment_service.model.Payment;
import pl.mp.ecommerce_platform.payment_service.producer.JmsProducer;
import pl.mp.ecommerce_platform.payment_service.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    JmsProducer jmsProducer;

    @Value("${ecommerce-platform.jms.order-update-dest}")
    private String orderUpdateDest;

    public Payment startPayment(Long orderId, double amount) {
        String urlToPayment = "XXXXXXXXXXXXXXXXXX";
        Payment payment = new Payment(null, orderId, amount, "IN_PROGRESS", urlToPayment, LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public void completePayment(Long paymentId) throws PaymentNotFoundException {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        boolean paymentSuccessful = new Random().nextBoolean();
        String status = paymentSuccessful ? "COMPLETED" : "FAILED";

        payment.setStatus(status);
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);
        Map<String,String> map = new HashMap<>();
        map.put("orderId", payment.getOrderId().toString());
        map.put("status", status);
        jmsProducer.sendMessage(orderUpdateDest, map);
    }

    public String getStatus(Long paymentId) throws PaymentNotFoundException {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
        return payment.getStatus();
    }

    public List<Payment> getInProgressPayments() {
        return paymentRepository.findByStatus("IN_PROGRESS");
    }
}
