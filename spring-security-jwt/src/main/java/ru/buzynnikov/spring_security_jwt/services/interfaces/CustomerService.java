package ru.buzynnikov.spring_security_jwt.services.interfaces;

import ru.buzynnikov.spring_security_jwt.models.Customer;

public interface CustomerService {

    Customer loadUserByUsername(String userName);

    void updateCustomer(Customer customer);
}
