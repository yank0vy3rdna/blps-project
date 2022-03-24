package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;
    private final UserMapper userMapper;

    @GetMapping("/")
    List<Project> allProducts() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    Project getProjectById(@PathVariable Integer id) {
        return projectMapper.findProjectById(id);
    }

    @PutMapping("/")
    Project createProject(@RequestBody Project project) throws AuthenticationException {
        var user = getUserFromContext();
        projectService.createProject(user.getId(), project);
        return project;
    }

    @GetMapping("/my")
    List<Project> myProjects() {
        User user = getUserFromContext();

        return projectMapper.getInitializedProjects(user.getId());
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
