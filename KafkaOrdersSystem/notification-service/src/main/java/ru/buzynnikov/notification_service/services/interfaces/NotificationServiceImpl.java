package ru.buzynnikov.notification_service.services.interfaces;

import org.springframework.stereotype.Service;
import ru.buzynnikov.notification_service.aspects.ToLog;
import ru.buzynnikov.notification_service.messages.out.KafkaSender;
import ru.buzynnikov.notification_service.models.Notification;
import ru.buzynnikov.notification_service.repositories.NotificationRepository;
import ru.buzynnikov.notification_service.services.impl.NotificationService;

import javax.swing.*;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final KafkaSender kafkaSender;

    public NotificationServiceImpl(NotificationRepository notificationRepository, KafkaSender kafkaSender) {
        this.notificationRepository = notificationRepository;
        this.kafkaSender = kafkaSender;
    }

    @ToLog
    @Override
    public void sendNotification(UUID orderId) {
        Notification savedNotification = notificationRepository.save(createNotification(orderId));
        sendNotificationToUser(savedNotification);
        sendToKafka(savedNotification);
    }


    private Notification createNotification(UUID orderId) {
        Notification notification = new Notification();
        notification.setOrderId(orderId);
        return notification;
    }

    private void sendNotificationToUser(Notification notification) {
        System.out.println("Сообщение отправлено пользователю");
    }

    private void sendToKafka(Notification notification) {
        kafkaSender.sendNotification(notification.getOrderId(),"delivered_order");
    }
}
