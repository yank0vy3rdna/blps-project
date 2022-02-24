package ru.itmo.blps.services;

import ru.itmo.blps.DAO.entities.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    int createProject(Integer uid ,Project project);
    int updateCurrentMoney(Integer projectId,Integer changes);
    int setStatus(Integer projectId, Integer status);
}
