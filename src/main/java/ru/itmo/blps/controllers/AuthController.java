package ru.itmo.blps.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.UserMapper;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserMapper mapper;

    public AuthController(UserMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        User user = (principal instanceof User) ? (User) principal : null;
        return Objects.nonNull(user) ? this.mapper.findUserByLogin(user.getLogin()) : null;
    }
}
