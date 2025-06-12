package ru.buzynnikov.notification_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.notification_service.models.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
