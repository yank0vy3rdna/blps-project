package ru.itmo.blps.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.Initiator_project;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.IPMapper;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.services.Exceptions.NoEnoughMoneyException;
import ru.itmo.blps.services.Exceptions.NoSuchProjectException;
import ru.itmo.blps.services.Exceptions.NoSuchUserException;
import ru.itmo.blps.services.Exceptions.WrongStatusException;
import ru.itmo.blps.services.projectService;

import java.util.List;

@Service
public class projectServiceImpl implements projectService {
    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IPMapper ipMapper;

    @Override
    public List<Project> getAllProjects() {
        return projectMapper.findAllProjects();
    }

    @Override
    public int createProject(Integer uid ,Project project) {
        User user = userMapper.findUserById(uid);
        if (user == null)
            throw new NoSuchUserException("Only user can create project.");

        // When we create the project, the id will be set in the obj back.
        int effectRow = projectMapper.insertProject(project);

        Initiator_project ip = new Initiator_project(uid, project.getId());
        ipMapper.insertIP(ip);

        return effectRow;
    }

    // When we take money away or back, we call this func.
    @Override
    public int updateCurrentMoney(Integer projectId, Integer changes) {
        Project selectedProject = projectMapper.findProjectById(projectId);
        if (selectedProject == null)
            throw new NoSuchProjectException("Can't find this project!");
        Integer currentMoney = selectedProject.getCurrent_amount();
        if (changes < 0 && currentMoney + changes < 0)
            throw new NoEnoughMoneyException("Can't take so much money!");
        return projectMapper.updateCurrentMoney(projectId,currentMoney + changes);
    }

    @Override
    public int setStatus(Integer projectId, Integer status) {
        if (status != 0 && status != 1 && status !=2)
            throw new WrongStatusException("No such status.");
        return projectMapper.updateStatus(projectId, status);
    }
}
