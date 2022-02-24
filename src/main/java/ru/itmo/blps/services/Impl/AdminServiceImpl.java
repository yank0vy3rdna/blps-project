package ru.itmo.blps.services.Impl;

import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.services.Exceptions.NoSuchProjectException;
import ru.itmo.blps.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService {


    private final ProjectMapper projectMapper;

    public AdminServiceImpl(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public int setStatus(Integer pid, Integer statusCode) {
        Project project = projectMapper.findProjectById(pid);
        if (project == null) throw new NoSuchProjectException("Can't find this project.");

        return projectMapper.updateStatus(pid, statusCode);
    }
}
