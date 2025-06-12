package ru.buzynnikov.notification_service.services.impl;

import java.util.UUID;

public interface NotificationService {
    void sendNotification(UUID orderId);
}
