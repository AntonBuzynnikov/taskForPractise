package ru.buzynnikov.consumer.messages;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;



@Component
public class KafkaListenerCustom {

    @KafkaListener(topics = "web-logs", groupId = "web-logs-group")
    public void listen(String message, Acknowledgment acknowledgment) {
        System.out.println(message);
        acknowledgment.acknowledge();
    }
}
