package ru.buzynnikov.spring_data_projections.services;

import ru.buzynnikov.spring_data_projections.models.Department;

public interface DepartmentService {
    Department getDepartment(Long id);
}
