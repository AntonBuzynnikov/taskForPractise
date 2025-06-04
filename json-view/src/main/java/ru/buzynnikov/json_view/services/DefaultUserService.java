package ru.buzynnikov.json_view.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.json_view.dto.CreateUserDto;
import ru.buzynnikov.json_view.exceptions.UserNotFoundException;
import ru.buzynnikov.json_view.models.User;
import ru.buzynnikov.json_view.repositoies.UserRepository;
import ru.buzynnikov.json_view.services.interfaces.UserService;

import java.util.Set;

@Service
public class DefaultUserService implements UserService{


    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(CreateUserDto createUserDto) {
        return userRepository.save(new User(createUserDto.name(), createUserDto.email()));
    }

    @Override
    public User getFullInfoById(Long id) {
        return userRepository.findUserWithOrdersById(id).orElseThrow(() -> new UserNotFoundException("User not found by id: " + id));
    }

    @Override
    public Set<User> getAll() {
        return userRepository.findAllWithFullData();
    }

    @Override
    public void update(CreateUserDto user, Long id) {
        User userFromDb = userRepository.findUserWithoutOrdersById(id).orElseThrow(() -> new UserNotFoundException("User not found by id: " + id));
        userFromDb.setName(user.name());
        userFromDb.setEmail(user.email());
        userRepository.save(userFromDb);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
