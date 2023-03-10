package ru.lazarenko.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.lazarenko.filmorate.exception.ValidationException;
import ru.lazarenko.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private int idUser = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Request add new User");

        if (users.values()
                .stream()
                .anyMatch(userSaved -> userSaved.getLogin().equals(user.getLogin()))) {
            log.error("User with login {} already exist", user.getLogin());
            throw new ValidationException("User with login='" + user.getLogin() + "' already exist");
        }
        user.setId(idUser++);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);

        log.info("Successful added new user {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("Request update user");

        if (user.getId() == null || user.getId() <= 0) {
            log.error("Id updatable user must not be null or less than 1");
            throw new ValidationException("Invalid user id='" + user.getId() + "' of updatable user");
        }

        if (!users.containsKey(user.getId())) {
            log.error("User with id='" + user.getId() + "' is not exist");
            throw new ValidationException("Invalid user id='" + user.getId() + "' of updatable user");
        }

        users.put(user.getId(), user);
        log.info("Updatable user {}", user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Request get all users");
        return new ArrayList<>(users.values());
    }
}