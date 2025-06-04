package ru.buzynnikov.json_view.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for {@link ru.buzynnikov.json_view.models.OrderItem}
 */
public record CreateOrderItemDto(@NotNull(message = "Id продукта должен быть не пустым")
                                 Long productId,
                                 @NotNull(message = "Количество продукта должно быть не пустым")
                                 @Positive(message = "Количество продукта должно быть больше нуля")
                                 Integer quantity) {
}