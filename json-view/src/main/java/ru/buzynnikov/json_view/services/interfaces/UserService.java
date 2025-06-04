package ru.buzynnikov.json_view.services.interfaces;

import ru.buzynnikov.json_view.dto.CreateUserDto;
import ru.buzynnikov.json_view.models.User;

import java.util.Set;

public interface UserService {

    User create(CreateUserDto createUserDto);

    User getFullInfoById(Long id);

    Set<User> getAll();

    void update(CreateUserDto user, Long id);

    void delete(Long id);
}
