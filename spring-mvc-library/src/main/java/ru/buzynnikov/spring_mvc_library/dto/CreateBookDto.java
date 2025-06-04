package ru.buzynnikov.spring_mvc_library.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateBookDto(@NotNull(message = "Имя не должно быть пустым")
                            @Size(min = 3, max = 255, message = "Название должно быть от 3 символов до 255 символов")
                            String name,
                            @NotNull(message = "ID автора не должен быть пустым")
                            Long authorId) {
}
