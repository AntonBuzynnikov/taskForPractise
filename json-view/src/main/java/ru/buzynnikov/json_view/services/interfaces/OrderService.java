package ru.buzynnikov.json_view.services.interfaces;

import ru.buzynnikov.json_view.dto.CreateOrderDto;
import ru.buzynnikov.json_view.models.UserOrder;

public interface OrderService {

    UserOrder create(CreateOrderDto dto, Long userId);

    void update(CreateOrderDto dto, Long orderId);

    void delete(Long orderId);

    UserOrder getById(Long orderId);
}
