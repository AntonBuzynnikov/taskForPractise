package ru.buzynnikov.spring_data_projections.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.buzynnikov.spring_data_projections.dto.CreateEmployeeRequest;
import ru.buzynnikov.spring_data_projections.exceptions.EmployeeNotFoundException;
import ru.buzynnikov.spring_data_projections.models.Department;
import ru.buzynnikov.spring_data_projections.models.Employee;
import ru.buzynnikov.spring_data_projections.projections.EmployeeProjection;
import ru.buzynnikov.spring_data_projections.repositories.EmployeeRepository;
import ru.buzynnikov.spring_data_projections.services.interfaces.DepartmentService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void getEmployeeById_ShouldReturnEmployeeProjection_WhenEmployeeExists() {
        // Arrange
        Long id = 1L;
        EmployeeProjection projection = mock(EmployeeProjection.class);
        when(employeeRepository.findProjectedById(id)).thenReturn(Optional.of(projection));

        // Act
        EmployeeProjection result = employeeService.getEmployeeById(id);

        // Assert
        assertNotNull(result);
        assertEquals(projection, result);
        verify(employeeRepository).findProjectedById(id);
    }

    @Test
    void getEmployeeById_ShouldThrowException_WhenEmployeeNotExists() {
        // Arrange
        Long id = 99L;
        when(employeeRepository.findProjectedById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(id));
        verify(employeeRepository).findProjectedById(id);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfProjections() {
        // Arrange
        List<EmployeeProjection> projections = List.of(mock(EmployeeProjection.class), mock(EmployeeProjection.class));
        when(employeeRepository.findAllProjectedBy()).thenReturn(projections);

        // Act
        List<EmployeeProjection> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeRepository).findAllProjectedBy();
    }

    @Test
    void createEmployee_ShouldSaveAndReturnEmployee() {
        // Arrange
        CreateEmployeeRequest request = new CreateEmployeeRequest(
                "John", "Doe", "Developer", 100000.0, 1L
        );
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        when(departmentService.getDepartment(request.departmentId())).thenReturn(department);
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee emp = invocation.getArgument(0);
            emp.setId(1L);
            return emp;
        });

        // Act
        Employee result = employeeService.createEmployee(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("Developer", result.getPosition());
        assertEquals(100000.0, result.getSalary());
        assertEquals(department, result.getDepartment());

        verify(departmentService).getDepartment(request.departmentId());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_ShouldCallRepositoryDelete() {
        // Arrange
        Long id = 1L;
        doNothing().when(employeeRepository).deleteById(id);

        // Act
        employeeService.deleteEmployee(id);

        // Assert
        verify(employeeRepository).deleteById(id);
    }

    @Test
    void updateEmployee_ShouldUpdateExistingEmployee() {
        // Arrange
        Long id = 1L;
        CreateEmployeeRequest request = new CreateEmployeeRequest(
                "Updated", "Name", "Manager", 120000.0, 2L
        );
        Department newDepartment = new Department();
        newDepartment.setId(2L);
        newDepartment.setName("Management");

        Employee existingEmployee = new Employee();
        existingEmployee.setId(id);
        existingEmployee.setFirstName("Old");
        existingEmployee.setLastName("Name");
        existingEmployee.setPosition("Developer");
        existingEmployee.setSalary(100000.0);
        existingEmployee.setDepartment(new Department());

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(departmentService.getDepartment(request.departmentId())).thenReturn(newDepartment);
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        // Act
        employeeService.updateEmployee(id, request);

        // Assert
        assertEquals("Updated", existingEmployee.getFirstName());
        assertEquals("Name", existingEmployee.getLastName());
        assertEquals("Manager", existingEmployee.getPosition());
        assertEquals(120000.0, existingEmployee.getSalary());
        assertEquals(newDepartment, existingEmployee.getDepartment());

        verify(employeeRepository).findById(id);
        verify(departmentService).getDepartment(request.departmentId());
        verify(employeeRepository).save(existingEmployee);
    }

    @Test
    void updateEmployee_ShouldThrowException_WhenEmployeeNotExists() {
        // Arrange
        Long id = 99L;
        CreateEmployeeRequest request = new CreateEmployeeRequest(
                "John", "Doe", "Developer", 100000.0, 1L
        );
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(id, request));
        verify(employeeRepository).findById(id);
        verifyNoInteractions(departmentService);
        verify(employeeRepository, never()).save(any());
    }
}