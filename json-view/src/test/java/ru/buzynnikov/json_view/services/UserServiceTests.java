package ru.buzynnikov.json_view.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.buzynnikov.json_view.dto.CreateUserDto;
import ru.buzynnikov.json_view.models.User;
import ru.buzynnikov.json_view.repositoies.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser() {
        CreateUserDto createUserDto = new CreateUserDto("Федя", "fedor@gmail.com");
        User user = new User("Федя", "fedor@gmail.com");
        user.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = userService.create(createUserDto);
        assertEquals("Федя", result.getName());
    }
    @Test
    void getUserById() {
        User user = new User("Федя", "fedor@gmail.com");
        user.setId(1L);
        when(userRepository.findUserWithOrdersById(any(Long.class))).thenReturn(java.util.Optional.of(user));
        User result = userService.getFullInfoById(1L);
        assertEquals("Федя", result.getName());
        assertNull(result.getOrders());
    }
}
