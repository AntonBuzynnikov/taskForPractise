package ru.buzynnikov.spring_data_projections.services.interfaces;

import ru.buzynnikov.spring_data_projections.dto.CreateEmployeeRequest;
import ru.buzynnikov.spring_data_projections.models.Employee;
import ru.buzynnikov.spring_data_projections.projections.EmployeeProjection;

import java.util.List;

public interface EmployeeService {

    EmployeeProjection getEmployeeById(Long id);
    List<EmployeeProjection> getAllEmployees();
    Employee createEmployee(CreateEmployeeRequest createEmployeeRequest);
    void deleteEmployee(Long id);
    void updateEmployee(Long id, CreateEmployeeRequest updateEmployeeRequest);
}
