package ru.buzynnikov.payment_service.messages.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.buzynnikov.payment_service.aspects.ToLog;
import ru.buzynnikov.payment_service.dto.MessageFromKafka;
import ru.buzynnikov.payment_service.mappers.MessageFromKafkaMapper;
import ru.buzynnikov.payment_service.services.interfaces.PaymentService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KafkaListenerCustom {


    private final MessageFromKafkaMapper mapper;

    private final PaymentService paymentService;


    private final ExecutorService executorService;

    public KafkaListenerCustom(MessageFromKafkaMapper mapper, PaymentService paymentService) {
        this.mapper = mapper;
        this.paymentService = paymentService;
        this.executorService = Executors.newCachedThreadPool();
    }

    @ToLog
    @KafkaListener(topics = "new_order", groupId = "payment-service")
    void listen(String message, Acknowledgment acknowledgment) {
        System.out.println(message);
        MessageFromKafka messageFromKafka = mapper.map(message);
        executorService.submit(task(messageFromKafka, acknowledgment));
    }

    private Runnable task(MessageFromKafka messageFromKafka, Acknowledgment acknowledgment){
        return  () -> {
            try {
                Thread.sleep(5000); // имитация обработки платежа
                paymentService.makePayment(messageFromKafka.orderId(), messageFromKafka.totalPrice());
                acknowledgment.acknowledge();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        };
    }
}
