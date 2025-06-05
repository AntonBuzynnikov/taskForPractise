package ru.buzynnikov.spring_object_mapper.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.spring_object_mapper.exceptions.CustomerNotFoundException;
import ru.buzynnikov.spring_object_mapper.models.Customer;
import ru.buzynnikov.spring_object_mapper.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("Пользователь с id " + id + " не найден"));
    }
}
