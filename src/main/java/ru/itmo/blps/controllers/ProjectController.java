package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.auth.AuthCheck;
import ru.itmo.blps.services.ProjectService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;
    private final AuthCheck authCheck;

    @GetMapping("/")
    List<Project> allProducts() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    Project getProjectById(@PathVariable Integer id) {
        return projectMapper.findProjectById(id);
    }

    @PutMapping("/")
    Project createProject(@RequestBody Project project, Authentication authentication) throws AuthenticationException {
        User user = authCheck.authCheck(authentication, 0);

        projectService.createProject(user.getId(), project);
        return project;
    }

    @GetMapping("/my")
    List<Project> myProjects(Authentication authentication) {
        User user = authCheck.authCheck(authentication, 0);

        return projectMapper.getInitializedProjects(user.getId());
    }
}
