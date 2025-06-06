package ru.buzynnikov.spring_data_projections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.spring_data_projections.models.Employee;
import ru.buzynnikov.spring_data_projections.projections.EmployeeProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<EmployeeProjection> findAllProjectedBy();

    Optional<EmployeeProjection> findProjectedById(Long id);
}
