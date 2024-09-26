package pl.mp.ecommerce_platform.order_service.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;

import org.springframework.stereotype.Component;
import pl.mp.ecommerce_platform.order_service.service.OrderService;

import java.util.Map;

@Component
public class JmsConsumer {
    private static final Logger logger = LoggerFactory.getLogger(JmsConsumer.class);

    @Autowired
    OrderService orderService;

    @JmsListener(destination = "order-update-dest")
    public void receiveOrderUpdateMessage(Map<String, String> message) {
        logger.info("Received order update message: {}", message);
        orderService.updateStatus(Long.parseLong(message.get("orderId")), message.get("status"));
    }
}
