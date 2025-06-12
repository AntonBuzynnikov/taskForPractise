package ru.buzynnikov.order_service.dto;

import ru.buzynnikov.order_service.models.Status;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(UUID orderId, BigDecimal totalPrice, Status status){
}
