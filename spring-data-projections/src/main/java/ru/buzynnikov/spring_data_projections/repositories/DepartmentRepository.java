package ru.buzynnikov.spring_data_projections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.spring_data_projections.models.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
