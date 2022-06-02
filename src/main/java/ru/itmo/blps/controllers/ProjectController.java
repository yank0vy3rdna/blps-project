package ru.itmo.blps.controllers;

import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.services.CamundaRequestSender;
import ru.itmo.blps.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;
    private final UserMapper userMapper;
    private final CamundaRequestSender camundaRequestSender;

    @GetMapping("/")
    @Secured({"ROLE_ANONYMOUS", "ROLE_REGULAR"})
    List<Project> allProducts() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ANONYMOUS", "ROLE_REGULAR"})
    Project getProjectById(@PathVariable Integer id) {
        return projectMapper.findProjectById(id);
    }

    @PutMapping("/")
    @Secured({"ROLE_REGULAR"})
    void createProject(@RequestBody Project project, Authentication authentication) throws AuthenticationException, JSONException {
        User user = userMapper.findUserByLogin(authentication.getName());
        camundaRequestSender.createProject(user, project);
    }

    @GetMapping("/my")
    @Secured({"ROLE_REGULAR"})
    List<Project> myProjects(Authentication authentication) {
        User user = userMapper.findUserByLogin(authentication.getName());
        return projectMapper.getInitializedProjects(user.getId());
    }
}
