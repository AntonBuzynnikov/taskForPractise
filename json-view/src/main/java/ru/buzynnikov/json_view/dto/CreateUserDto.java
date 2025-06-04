package ru.buzynnikov.json_view.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link ru.buzynnikov.json_view.models.User}
 */
public record CreateUserDto(@NotNull(message = "Необходимо указать имя пользователя")
                            @Size(min = 3, max = 255, message = "Длина имени пользователя должна быть от 3 до 255 символов")
                            String name,
                            @NotNull(message = "Необходимо указать адрес электронной почты")
                            @Pattern(regexp = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)", message = "Некорректный формат email")
                            String email) {
}