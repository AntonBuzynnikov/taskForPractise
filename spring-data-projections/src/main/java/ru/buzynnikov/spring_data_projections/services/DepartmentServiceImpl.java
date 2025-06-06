package ru.buzynnikov.spring_data_projections.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.spring_data_projections.exceptions.DepartmentNotFoundException;
import ru.buzynnikov.spring_data_projections.models.Department;
import ru.buzynnikov.spring_data_projections.repositories.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department getDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(()-> new DepartmentNotFoundException("Департамент с id "+id+" не найден."));
    }

}
