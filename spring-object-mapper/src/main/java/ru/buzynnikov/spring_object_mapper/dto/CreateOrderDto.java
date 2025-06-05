package ru.buzynnikov.spring_object_mapper.dto;

import java.util.List;

public record CreateOrderDto(Long customerId, String shippingAddress, List<Long> productIds) {
}
