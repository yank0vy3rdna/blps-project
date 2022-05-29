package ru.itmo.blps.services.Impl;

import lombok.AllArgsConstructor;
import org.camunda.bpm.client.spring.annotation.EnableExternalTaskClient;
import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.services.Exceptions.NoEnoughMoneyException;
import ru.itmo.blps.services.Exceptions.NoSuchProjectException;
import ru.itmo.blps.services.Exceptions.NoSuchUserException;
import ru.itmo.blps.services.Exceptions.WrongStatusException;
import ru.itmo.blps.services.ProjectService;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    @Override
    public List<Project> getAllProjects() {
        return projectMapper.findAllProjects();
    }

    @Override
    public int createProject(Integer uid, Project project) {
        User user = userMapper.findUserById(uid);
        if (user == null)
            throw new NoSuchUserException("Only user can create project.");

        // When we create the project, the id will be set in the obj back.
        projectMapper.insertProject(project);
        projectMapper.addInitiator(uid, project.getId());
        return 0;
    }

    // When we take money away or back, we call this func.
    @Override
    public int updateCurrentMoney(Integer projectId, Integer changes) {
        Project selectedProject = projectMapper.findProjectById(projectId);
        if (selectedProject == null)
            throw new NoSuchProjectException("Can't find this project!");
        Integer currentMoney = selectedProject.getCurrentAmount();
        if (changes < 0 && currentMoney + changes < 0)
            throw new NoEnoughMoneyException("Can't take so much money!");
        return projectMapper.updateCurrentMoney(projectId, currentMoney + changes);
    }

    @Override
    public int setStatus(Integer projectId, Integer status) {
        if (status != 0 && status != 1 && status != 2)
            throw new WrongStatusException("No such status.");
        return projectMapper.updateStatus(projectId, status);
    }
}
