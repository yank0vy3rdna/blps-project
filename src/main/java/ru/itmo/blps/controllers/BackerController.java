package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.auth.AuthCheck;
import ru.itmo.blps.controllers.inputModel.BackModel;
import ru.itmo.blps.services.BackService;

import java.util.List;

@RestController
@RequestMapping("/backing")
@Secured({"ROLE_REGULAR"})
@AllArgsConstructor
public class BackerController {
    private final ProjectMapper projectMapper;
    private final BackService backService;
    private final AuthCheck authCheck;

    @Transactional
    @GetMapping("/projects/")
    List<Project> backedProjects(Authentication authentication) {
        User user = authCheck.authCheck(authentication, 0);
        return projectMapper.getBackedProjects(user.getId());
    }

    @Transactional(rollbackFor = PreAuthenticatedCredentialsNotFoundException.class)
    @PostMapping("/back/")
    void backProject(@RequestBody BackModel req, Authentication authentication) {
        User user = authCheck.authCheck(authentication, 0);
        backService.back(req.getProjectId(), user.getId(), req.getAmount());
    }

}
