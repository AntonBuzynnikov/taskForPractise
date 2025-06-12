package ru.buzynnikov.payment_service.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.buzynnikov.payment_service.dto.MessageFromKafka;
import ru.buzynnikov.payment_service.exceptions.BadRequestException;
import ru.buzynnikov.payment_service.models.Payment;

import java.math.BigDecimal;

@Component
public class MessageFromKafkaMapper {

    private final ObjectMapper objectMapper;

    public MessageFromKafkaMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public MessageFromKafka map(String message) {
        Payment payment = new Payment();
        try {
            MessageFromKafka messageFromKafka = objectMapper.readValue(message, MessageFromKafka.class);
            validateMessage(messageFromKafka);
            return messageFromKafka;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateMessage(MessageFromKafka messageFromKafka) {
        if(messageFromKafka.orderId() == null) throw new BadRequestException("Заказ не указан");
        if(messageFromKafka.totalPrice() == null) throw new BadRequestException("Сумма не указана");
        if(messageFromKafka.totalPrice().compareTo(BigDecimal.ZERO) <= 0) throw new BadRequestException("Сумма должна быть больше нуля");
    }


}
