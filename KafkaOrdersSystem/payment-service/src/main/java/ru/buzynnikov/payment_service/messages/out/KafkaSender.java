package ru.buzynnikov.payment_service.messages.out;

import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import ru.buzynnikov.payment_service.aspects.ToLog;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Component
public class KafkaSender {

    private final KafkaTemplate<String, UUID> kafkaTemplate;


    public KafkaSender(KafkaTemplate<String, UUID> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @ToLog
    @Retryable(
            retryFor = { KafkaException.class, TimeoutException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void sendNewOrder(UUID orderId, String topicName) {
        kafkaTemplate.send(topicName, orderId);
    }
}
