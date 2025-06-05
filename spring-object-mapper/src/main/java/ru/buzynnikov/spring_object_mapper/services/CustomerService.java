package ru.buzynnikov.spring_object_mapper.services;

import ru.buzynnikov.spring_object_mapper.models.Customer;

public interface CustomerService {
    Customer findById(Long id);
}
