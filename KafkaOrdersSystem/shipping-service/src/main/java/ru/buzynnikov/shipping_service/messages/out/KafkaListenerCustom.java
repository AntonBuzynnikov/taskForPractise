package ru.buzynnikov.shipping_service.messages.out;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.buzynnikov.shipping_service.aspects.ToLog;
import ru.buzynnikov.shipping_service.services.interfaces.ShippingService;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KafkaListenerCustom {
    private final ShippingService shippingService;

    private final ExecutorService executorService;



    public KafkaListenerCustom(ShippingService shippingService) {
        this.shippingService = shippingService;
        this.executorService = Executors.newCachedThreadPool();
    }

    @ToLog
    @KafkaListener(topics = "payed_order", groupId = "shipping-service")
    void payedOrder(String message, Acknowledgment acknowledgment) {
        UUID orderId = UUID.fromString(message.replace("\"", ""));
        System.out.println(orderId);
        executorService.submit(task(acknowledgment, orderId));
    }

    private Runnable task(Acknowledgment acknowledgment, UUID orderId){
        return  () -> {
            try {
                Thread.sleep(5000); //имитация отгрузки
                shippingService.shipping(orderId);
                acknowledgment.acknowledge();
                System.out.println("Отгружен заказ: "+orderId);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        };
    }
}
