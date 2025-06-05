package ru.buzynnikov.spring_object_mapper.services;

import ru.buzynnikov.spring_object_mapper.dto.CreateOrderDto;
import ru.buzynnikov.spring_object_mapper.models.Order;

public interface OrderService {

    Order createOrder(CreateOrderDto request);

    Order getOrderById(Long id);

}
