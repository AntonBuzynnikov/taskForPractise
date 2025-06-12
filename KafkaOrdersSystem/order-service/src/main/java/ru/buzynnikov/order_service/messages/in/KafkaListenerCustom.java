package ru.buzynnikov.order_service.messages.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.buzynnikov.order_service.aspects.ToLog;
import ru.buzynnikov.order_service.models.Status;
import ru.buzynnikov.order_service.services.interfaces.OrderService;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KafkaListenerCustom {

    private final OrderService orderService;

    private final ExecutorService executorService;


    public KafkaListenerCustom(OrderService orderService) {
        this.orderService = orderService;
        this.executorService = Executors.newCachedThreadPool();
    }

    @ToLog
    @KafkaListener(topics = "payed_order", groupId = "order-service")
    void paidOrder(String message, Acknowledgment acknowledgment) {
        Status status = Status.PAID;

        UUID orderId = UUID.fromString(message.replace("\"", ""));

        executorService.submit(task(acknowledgment, orderId, status));
    }

    @ToLog
    @KafkaListener(topics = "sent_order", groupId = "order-service")
    void sentOrder(String message, Acknowledgment acknowledgment) {
        Status status = Status.SHIPPED;

        UUID orderId = UUID.fromString(message.replace("\"", ""));

        executorService.submit(task(acknowledgment, orderId, status));
    }

    @ToLog
    @KafkaListener(topics = "delivered_order", groupId = "order-service")
    void deliveredOrder(String message, Acknowledgment acknowledgment) {
        Status status = Status.CANCELLED;

        UUID orderId = UUID.fromString(message.replace("\"", ""));

        executorService.submit(task(acknowledgment, orderId, status));
    }

    private Runnable task(Acknowledgment acknowledgment, UUID orderId, Status status){
        return  () -> {
            try {
                orderService.updateStatus(status, orderId);
                acknowledgment.acknowledge();
                System.out.println("Статус заказа изменён: " );
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        };
    }
}
