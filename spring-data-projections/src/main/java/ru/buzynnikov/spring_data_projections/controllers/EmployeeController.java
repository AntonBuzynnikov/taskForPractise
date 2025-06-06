package ru.buzynnikov.spring_data_projections.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.spring_data_projections.dto.CreateEmployeeRequest;
import ru.buzynnikov.spring_data_projections.models.Employee;
import ru.buzynnikov.spring_data_projections.projections.EmployeeProjection;
import ru.buzynnikov.spring_data_projections.services.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProjection> findById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeProjection>> findAll(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody @Valid CreateEmployeeRequest request, UriComponentsBuilder builder){
        Employee employee = employeeService.createEmployee(request);
        return ResponseEntity.created(builder.path("/employees/{id}").buildAndExpand(employee.getId()).toUri()).body(employee);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CreateEmployeeRequest request){
        employeeService.updateEmployee(id,request);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
