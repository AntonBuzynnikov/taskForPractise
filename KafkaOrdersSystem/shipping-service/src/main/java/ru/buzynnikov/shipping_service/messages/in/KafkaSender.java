package ru.buzynnikov.shipping_service.messages.in;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.buzynnikov.shipping_service.aspects.ToLog;

import java.util.UUID;

@Component
public class KafkaSender {

    private final KafkaTemplate<String, UUID> kafkaTemplate;

    public KafkaSender(KafkaTemplate<String, UUID> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @ToLog
    public void sendMessage(String topic, UUID orderId) {
        kafkaTemplate.send(topic, orderId);
        System.out.println("Отправлено сообщение: " + orderId);
    }
}
