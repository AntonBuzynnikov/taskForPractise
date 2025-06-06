package ru.buzynnikov.spring_data_projections.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link ru.buzynnikov.spring_data_projections.models.Employee}
 */
public record CreateEmployeeRequest(
        @NotNull(message = "Имя не должно быть пустым")
        @Size(message = "Имя должно быть от 3 до 50 символов", min = 3, max = 50)
        String firstName,
        @NotNull(message = "Фамилия не должна быть пустой")
        @Size(message = "Фамилия должна быть от 3 до 50 символов", min = 3, max = 50)
        String lastName,
        @NotNull(message = "Позиция не должна быть пустой")
        @Size(message = "Позиция должна быть от 3 до 50 символов", min = 3, max = 50)
        String position,
        @Min(message = "Зарплата должна быть больше или равна 1000", value = 1000)
        @NotNull(message = "Зарплата не должна быть пустой")
        double salary,
        @NotNull(message = "id отдела не должен быть пустым")
        Long departmentId) {}