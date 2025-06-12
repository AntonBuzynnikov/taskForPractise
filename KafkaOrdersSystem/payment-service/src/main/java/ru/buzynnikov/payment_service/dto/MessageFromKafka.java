package ru.buzynnikov.payment_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MessageFromKafka(UUID orderId, BigDecimal totalPrice) {
}
