package ru.buzynnikov.notification_service.messages.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.buzynnikov.notification_service.aspects.ToLog;
import ru.buzynnikov.notification_service.services.impl.NotificationService;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KafkaListenerCustom {

    private final ExecutorService executorService;
    private final NotificationService notificationService;

    public KafkaListenerCustom(NotificationService notificationService) {
        this.notificationService = notificationService;
        this.executorService = Executors.newCachedThreadPool();
    }

    @ToLog
    @KafkaListener(topics = "sent_order", groupId = "payment-service")
    void listen(String message, Acknowledgment acknowledgment) {
        System.out.println(message);
        UUID orderId = UUID.fromString(message.substring(1, message.length() - 1));
        executorService.submit(task(orderId, acknowledgment));
    }

    private Runnable task(UUID orderId, Acknowledgment acknowledgment){
        return  () -> {
            try {
                notificationService.sendNotification(orderId);
                acknowledgment.acknowledge();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        };
    }
}
