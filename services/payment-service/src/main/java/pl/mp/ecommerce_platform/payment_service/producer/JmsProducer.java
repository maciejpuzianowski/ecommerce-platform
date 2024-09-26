package pl.mp.ecommerce_platform.payment_service.producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JmsProducer {
    private static final Logger logger = LoggerFactory.getLogger(JmsProducer.class);
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String destination, Map<String, String> message) {
        jmsTemplate.convertAndSend(destination, message);
        logger.info("Sent message to {}: {}", destination, message);
    }
}
