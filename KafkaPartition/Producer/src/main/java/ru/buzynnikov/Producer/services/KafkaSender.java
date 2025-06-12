package ru.buzynnikov.Producer.services;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.buzynnikov.Producer.models.Message;

import java.util.UUID;

@Service
public class KafkaSender {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public KafkaSender(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Scheduled(fixedRate = 1)
    public void sendMessage() {
        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setText("test message");
        kafkaTemplate.send("web-logs", message.getId().toString(), message);
    }

}
