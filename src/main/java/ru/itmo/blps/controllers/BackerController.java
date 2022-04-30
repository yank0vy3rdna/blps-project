package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.controllers.inputModel.BackModel;
import ru.itmo.blps.services.BackMessageProducerService;
import ru.itmo.blps.services.BackService;

import java.util.List;

@RestController
@RequestMapping("/backing")
@Secured({"ROLE_REGULAR"})
@AllArgsConstructor
public class BackerController {
    private final ProjectMapper projectMapper;
    private final BackMessageProducerService backMessageProducerService;
    private final UserMapper userMapper;

    @Transactional
    @GetMapping("/projects/")
    List<Project> backedProjects(Authentication authentication) {
        User user = userMapper.findUserByLogin(authentication.getName());
        return projectMapper.getBackedProjects(user.getId());
    }

    @Transactional(rollbackFor = PreAuthenticatedCredentialsNotFoundException.class)
    @PostMapping("/back/")
    void backProject(@RequestBody BackModel req, Authentication authentication) {
        // User user = userMapper.findUserByLogin(authentication.getName());

        // We should set backer id in BackModel too.

        backMessageProducerService.sendBackMessage("back", req);
    }

}
