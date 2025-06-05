package ru.buzynnikov.spring_object_mapper.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.buzynnikov.spring_object_mapper.exceptions.CustomerNotFoundException;
import ru.buzynnikov.spring_object_mapper.models.Customer;
import ru.buzynnikov.spring_object_mapper.repositories.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer testCustomer;
    private final Long customerId = 1L;
    private final Long nonExistentCustomerId = 999L;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setCustomerId(customerId);
        testCustomer.setFirstName("John");
        testCustomer.setLastName("Doe");
        testCustomer.setEmail("john.doe@example.com");
        testCustomer.setContactNumber("+1234567890");
    }

    @Test
    void findById_WhenCustomerExists_ShouldReturnCustomer() {
        // Arrange
        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(testCustomer));

        // Act
        Customer foundCustomer = customerService.findById(customerId);

        // Assert
        assertNotNull(foundCustomer);
        assertEquals(customerId, foundCustomer.getCustomerId());
        assertEquals("John", foundCustomer.getFirstName());
        assertEquals("Doe", foundCustomer.getLastName());
        assertEquals("john.doe@example.com", foundCustomer.getEmail());
        assertEquals("+1234567890", foundCustomer.getContactNumber());

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void findById_WhenCustomerNotExists_ShouldThrowException() {
        // Arrange
        when(customerRepository.findById(nonExistentCustomerId))
                .thenReturn(Optional.empty());

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.findById(nonExistentCustomerId)
        );

        assertEquals("Пользователь с id " + nonExistentCustomerId + " не найден",
                exception.getMessage());
        verify(customerRepository, times(1)).findById(nonExistentCustomerId);
    }

}