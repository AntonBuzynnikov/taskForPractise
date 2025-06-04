package ru.buzynnikov.json_view.services.interfaces;

public interface OrderItemService {

    void update(Long orderId, Long productId, Integer itemQuantity);
}
