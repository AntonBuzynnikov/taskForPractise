package ru.buzynnikov.spring_security_jwt.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.spring_security_jwt.models.Customer;
import ru.buzynnikov.spring_security_jwt.repositories.CustomerRepository;
import ru.buzynnikov.spring_security_jwt.services.interfaces.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer loadUserByUsername(String userName) {
        return customerRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
