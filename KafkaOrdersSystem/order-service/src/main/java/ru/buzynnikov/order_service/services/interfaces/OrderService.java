package ru.buzynnikov.order_service.services.interfaces;

import ru.buzynnikov.order_service.dto.CreateOrderRequest;
import ru.buzynnikov.order_service.dto.OrderResponse;
import ru.buzynnikov.order_service.models.Status;

import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getOrder(UUID orderId);

    void updateStatus(Status status, UUID orderId);
}
