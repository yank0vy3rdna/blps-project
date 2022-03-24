package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.controllers.inputModel.BackModel;
import ru.itmo.blps.services.BackService;

import java.util.List;

@RestController
@RequestMapping("/backing")
@AllArgsConstructor
public class BackerController {
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final BackService backService;

    @Transactional
    @GetMapping("/projects/")
    List<Project> backedProjects() {
        var user = getUserFromContext();
        return projectMapper.getBackedProjects(user.getId());
    }

    @Transactional(rollbackFor = PreAuthenticatedCredentialsNotFoundException.class)
    @PostMapping("/back/")
    void backProject(@RequestBody BackModel req) {
        User user = getUserFromContext();
        backService.back(req.getProjectId(), user.getId(), req.getAmount());
    }

    private User getUserFromContext() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new PreAuthenticatedCredentialsNotFoundException("auth error");
        }
        if (!(principal instanceof UserDetails)) {
            throw new PreAuthenticatedCredentialsNotFoundException("principal is not user");
        }
        UserDetails userDetails = (UserDetails) principal;
        return userMapper.findUserByLogin(userDetails.getUsername());
    }
}
