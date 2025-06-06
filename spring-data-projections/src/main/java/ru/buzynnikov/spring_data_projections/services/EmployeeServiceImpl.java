package ru.buzynnikov.spring_data_projections.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.spring_data_projections.dto.CreateEmployeeRequest;
import ru.buzynnikov.spring_data_projections.exceptions.EmployeeNotFoundException;
import ru.buzynnikov.spring_data_projections.models.Employee;
import ru.buzynnikov.spring_data_projections.projections.EmployeeProjection;
import ru.buzynnikov.spring_data_projections.repositories.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    @Override
    public EmployeeProjection getEmployeeById(Long id) {
        return employeeRepository.findProjectedById(id)
                .orElseThrow(()->new EmployeeNotFoundException("Сотрудник с идентификатором "+id+" не найден"));
    }

    @Override
    public List<EmployeeProjection> getAllEmployees() {
        return employeeRepository.findAllProjectedBy();
    }

    @Override
    public Employee createEmployee(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setPosition(request.position());
        employee.setSalary(request.salary());
        employee.setDepartment(departmentService.getDepartment(request.departmentId()));
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void updateEmployee(Long id, CreateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException("Сотрудник с идентификатором "+id+" не найден"));
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setPosition(request.position());
        employee.setSalary(request.salary());
        employee.setDepartment(departmentService.getDepartment(request.departmentId()));
        employeeRepository.save(employee);
    }
}
