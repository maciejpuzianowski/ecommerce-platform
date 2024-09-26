package pl.mp.ecommerce_platform.payment_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.mp.ecommerce_platform.payment_service.exception.PaymentNotFoundException;

@Component
public class ScheduledTasksService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasksService.class);

    @Autowired
    private PaymentService paymentService;

    @Scheduled(fixedRate = 20000)
    public void simulatePaymentProcessing() {
        logger.info("Simulating payment processing");
        paymentService.getInProgressPayments().forEach((payment) -> {
            try {
                logger.info("Completing payment: {}", payment.getId());
                paymentService.completePayment(payment.getId());
            } catch (PaymentNotFoundException e) {
                logger.error("Payment not found while simulating", e);
            }
        });
    }
}
