package ru.buzynnikov.json_view.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.json_view.dto.CreateUserDto;
import ru.buzynnikov.json_view.mappers.Views;
import ru.buzynnikov.json_view.models.User;
import ru.buzynnikov.json_view.services.interfaces.UserService;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @JsonView(Views.UserDetails.class)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getFullInfoById(id));
    }

    @GetMapping
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<Set<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserDto createUserDto, UriComponentsBuilder builder) {
        User user = userService.create(createUserDto);
        return ResponseEntity.created(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri()).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserById(@PathVariable("id") Long id, @RequestBody @Valid CreateUserDto createUserDto) {
        userService.update(createUserDto, id);
        return ResponseEntity.noContent().build();
    }
}
