package ru.buzynnikov.json_view.dto;

import jakarta.validation.constraints.NotNull;
import ru.buzynnikov.json_view.models.UserOrder;

import java.util.List;

/**
 * DTO for {@link UserOrder}
 */
public record CreateOrderDto(@NotNull(message = "Список товаров должен быть заполнен.") List<CreateOrderItemDto> items) {
}