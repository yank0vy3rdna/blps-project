package ru.itmo.blps.controllers;

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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController extends BasicController {
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;
    private final UserMapper userMapper;

    public ProjectController(ProjectMapper projectMapper, ProjectService projectService, UserMapper userMapper) {
        this.projectMapper = projectMapper;
        this.projectService = projectService;
        this.userMapper = userMapper;
    }


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
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new PreAuthenticatedCredentialsNotFoundException("auth error");
        }
        if (!(principal instanceof UserDetails)) {
            throw new PreAuthenticatedCredentialsNotFoundException("principal is not user");
        }
        UserDetails userDetails = (UserDetails) principal;
        User user = userMapper.findUserByLogin(userDetails.getUsername());
        projectService.createProject(user.getId(), project);
        return project;
    }

    @GetMapping("/my")
    List<Project> myProjects(Principal principal) {
        if (!(principal instanceof User)) {
            throw new PreAuthenticatedCredentialsNotFoundException("principal is not user");
        }
        User user = (User) principal;
        return projectMapper.getInitializedProjects(user.getId());
    }
}
