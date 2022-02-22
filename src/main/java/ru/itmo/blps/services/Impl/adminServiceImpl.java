package ru.itmo.blps.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.services.Exceptions.NoSuchProjectException;
import ru.itmo.blps.services.adminService;

public class adminServiceImpl implements adminService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public int setStatus(Integer pid, Integer statusCode) {
        Project project = projectMapper.findProjectById(pid);
        if (project == null) throw new NoSuchProjectException("Can't find this project.");

        return projectMapper.updateStatus(pid, statusCode);
    }
}
