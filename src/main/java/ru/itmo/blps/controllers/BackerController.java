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
import ru.itmo.blps.controllers.inputModel.BackRequestModel;
import ru.itmo.blps.services.CamundaRequestSender;

import java.util.List;

@RestController
@RequestMapping("/backing")
@AllArgsConstructor
public class BackerController {
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final CamundaRequestSender camundaRequestSender;

    @Secured({"ROLE_REGULAR"})
    @Transactional
    @GetMapping("/projects/")
    List<Project> backedProjects(Authentication authentication) {
        User user = userMapper.findUserByLogin(authentication.getName());
        return projectMapper.getBackedProjects(user.getId());
    }

    @Secured({"ROLE_REGULAR"})
    @Transactional(rollbackFor = PreAuthenticatedCredentialsNotFoundException.class)
    @PostMapping("/back/")
    void backProject(@RequestBody BackRequestModel req, Authentication authentication) {
        User user = userMapper.findUserByLogin(authentication.getName());
        camundaRequestSender.backProject(user, req.getAmount(), req.getProjectName());
    }

}
