package ru.buzynnikov.order_service.messages.out;

import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import ru.buzynnikov.order_service.aspects.ToLog;
import ru.buzynnikov.order_service.models.Order;

import java.util.concurrent.TimeoutException;

@Component
public class KafkaSender {

    private final KafkaTemplate<String, Order> kafkaTemplate;


    public KafkaSender(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @ToLog
    @Retryable(
            retryFor = { KafkaException.class, TimeoutException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void sendNewOrder(Order order, String topicName) {
        kafkaTemplate.send(topicName, order);
        System.out.println("Сообщение отправлено: " + order.toString() + " на топик: " + topicName + "\n");
    }


}

