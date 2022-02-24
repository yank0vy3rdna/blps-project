package ru.itmo.blps.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.services.ProjectService;

import java.util.List;

@RestController
public class ProjectController {
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;

    public ProjectController(ProjectMapper projectMapper, ProjectService projectService) {
        this.projectMapper = projectMapper;
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    void createProject() {
        var project = new Project();
        project.setName("lol");
        this.projectService.createProject(1, project);
    }

    @GetMapping("/project/backers")
    List<User> getInitiators() {
        return projectMapper.getBackers(1);
    }
}
