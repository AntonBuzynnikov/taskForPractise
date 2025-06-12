package ru.buzynnikov.order_service.dto;


import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull(message = "Пользователь не может быть пустым")
        Long userId,
        @NotNull(message = "Товары не могут быть пустыми")
        List<Long> productsId) {
}
