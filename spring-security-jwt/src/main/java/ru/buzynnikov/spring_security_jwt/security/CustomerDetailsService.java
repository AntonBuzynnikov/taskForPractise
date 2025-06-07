package ru.buzynnikov.spring_security_jwt.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.buzynnikov.spring_security_jwt.models.Customer;
import ru.buzynnikov.spring_security_jwt.models.Role;
import ru.buzynnikov.spring_security_jwt.services.interfaces.CustomerService;

import java.util.Collection;
import java.util.List;

@Component
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerService customerService;

    public CustomerDetailsService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerService.loadUserByUsername(username);
        System.out.println("Locked user: " + customer.isAccountNotLocking());
        if(!customer.isAccountNotLocking()) throw new RuntimeException("User is locking");

        return new User(customer.getUsername(), customer.getPassword(), getAuthorities(customer.getRole()));
    }

    public void increaseFailedAttempts(String username) {
        Customer customer = customerService.loadUserByUsername(username);
        customer.setFailedLoginAttempts(customer.getFailedLoginAttempts() + 1);

        if (customer.getFailedLoginAttempts() >= 5) {
            customer.setAccountNotLocking(false);
        }
        customerService.updateCustomer(customer);
    }


    private Collection<? extends GrantedAuthority> getAuthorities(Role role){
        return List.of(new SimpleGrantedAuthority(role.name()));
    }



}
