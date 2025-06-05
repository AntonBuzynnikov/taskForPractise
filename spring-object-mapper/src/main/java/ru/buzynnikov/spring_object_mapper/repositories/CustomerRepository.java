package ru.buzynnikov.spring_object_mapper.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.buzynnikov.spring_object_mapper.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}