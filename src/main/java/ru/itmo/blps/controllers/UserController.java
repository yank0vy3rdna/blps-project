package ru.itmo.blps.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.blps.mappers.UserMapper;
import ru.itmo.blps.models.User;

@RestController
class UserController {
    private final UserMapper userMapper;

    UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    Integer all() {
        User user = new User(null, "login", "password");
        userMapper.insertUser(user);
        return user.getId();
    }
}
